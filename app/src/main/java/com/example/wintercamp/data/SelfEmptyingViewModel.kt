package com.example.wintercamp.data

import android.net.Uri
import android.util.Log
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.wintercamp.App.Companion.context
import com.example.wintercamp.R
import com.example.wintercamp.network.HttpUtil
import com.example.wintercamp.network.ServerPath
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

private const val TAG = "SelfEmptyingViewModel"

sealed class Operation(val message: String) {
    object Up : Operation(context.getString(R.string.go_up))
    object Down : Operation(context.getString(R.string.go_down))
    object Left : Operation(context.getString(R.string.go_left))
    object Right : Operation(context.getString(R.string.go_right))
    object Move : Operation(context.getString(R.string.move))
    object Welcome : Operation(context.getString(R.string.hello_everyone))


    companion object {

        fun getRandom(): Operation = getOperation((0..5).random())
        private fun getOperation(int: Int) =
            when (int) {
                0 -> Up
                1 -> Down
                2 -> Left
                3 -> Right
                4 -> Move
                else -> Welcome
            }

    }
}

class SelfEmptyingViewModel : ViewModel() {
    data class Robot(
        val robotX: Dp = 0.dp,
        val robotY: Dp = 0.dp,
        val robotName: String = "",
        val robotMessage: String = "",
        val robotID: Int = 0,
        val robotVisible: Boolean = false
    )

    val user: MutableStateFlow<Robot> = MutableStateFlow(Robot())
    var robotList: List<MutableStateFlow<Robot>> = emptyList()
    val exoPlayer1 = ExoPlayer.Builder(context).build()
    val exoPlayer2 = ExoPlayer.Builder(context).build()

    fun stopPlaying() {
        exoPlayer1.stop()
        exoPlayer2.stop()
    }

    fun startPlaying() {
        val uriStr1 = """android.resource://${context.packageName}/${R.raw.self_emptying_bgm}"""
        val uri1 = Uri.parse(uriStr1)
        exoPlayer1.setMediaItem(MediaItem.fromUri(uri1))
        exoPlayer1.prepare()
        exoPlayer1.repeatMode = REPEAT_MODE_ALL
        exoPlayer1.play()
        val uriStr2 = """android.resource://${context.packageName}/${R.raw.wooden_fish_bgm}"""
        val uri2 = Uri.parse(uriStr2)
        exoPlayer2.setMediaItem(MediaItem.fromUri(uri2))
        exoPlayer2.prepare()
        exoPlayer2.repeatMode = REPEAT_MODE_ALL
        exoPlayer2.play()
    }

    fun userOperation(operation: Operation) =
        operate(operation = operation, stateFlow = user)

    fun randomOperation(operation: Operation = Operation.getRandom(), randomRobot: Int) =
        operate(operation = operation, stateFlow = robotList[randomRobot])

    private fun operate(operation: Operation, stateFlow: MutableStateFlow<Robot>) {
        stateFlow.update {
            it.copy(
                robotX = when (operation) {
                    Operation.Left -> it.robotX - 20.dp
                    Operation.Move -> (0..300).random().dp
                    Operation.Right -> it.robotX + 20.dp
                    else -> it.robotX
                },
                robotY = when (operation) {
                    Operation.Up -> it.robotY - 20.dp
                    Operation.Move -> (100..500).random().dp
                    Operation.Down -> it.robotY + 20.dp
                    else -> it.robotY
                },
                robotMessage = operation.message
            )
        }
    }

    fun show(stateFlow: MutableStateFlow<Robot>) {
        stateFlow.update { it.copy(robotVisible = true) }
    }

    fun hide(stateFlow: MutableStateFlow<Robot>) {
        stateFlow.update { it.copy(robotVisible = false) }
    }

    fun initAll(
        selfName: String,
    ) {
        robotList = robotNames.map { name ->
            MutableStateFlow(
                Robot(
                    robotName = name,
                    robotX = (0..300).random().dp,
                    robotY = (100..500).random().dp
                )
            )
        }
        user.update {
            it.copy(
                robotX = (0..300).random().dp,
                robotY = (100..500).random().dp,
                robotName = selfName
            )
        }
    }

    fun getUsersRequest() {
        HttpUtil.sendOkHttpRequest(
            "${ServerPath.httpPath}/users",
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, e.toString())
                }

                override fun onResponse(
                    call: Call,
                    response: Response
                ) {
                    val responseData = response.body?.string()
                    if (responseData != null) {
                        try {
                            val jsonArray = JSONArray(responseData)
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                Log.d(TAG, jsonObject.getString("name"))
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, e.toString())
                        }
                    }
                }

            })
    }

}
