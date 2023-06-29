package com.example.wintercamp.network

import android.graphics.Paint.Style
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.dp
import com.example.wintercamp.App
import com.example.wintercamp.R
import com.example.wintercamp.data.SelfEmptyingViewModel
import com.example.wintercamp.questionnaire.DecodeUsers
import com.example.wintercamp.questionnaire.EncodeUser
import com.example.wintercamp.questionnaire.UserOnline
import com.example.wintercamp.ui.screen.toOperation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

private const val TAG = "WebSocket"

object WebSocket {

    lateinit var webSocket: WebSocket

    fun webSocketConnect(
        account: String = "",
        selfEmptyingViewModel: SelfEmptyingViewModel
    ) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(ServerPath.wsPath)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket 连接已打开")
                val user = EncodeUser(UserOnline(account_online = account, type_online = 0))
                webSocket.send(user)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(TAG, "收到服务器消息：$text")
                val users = DecodeUsers(text)
                for (user in users) {
                    if (user.type_online == 0 || user.type_online == 3) {
                        selfEmptyingViewModel.robotList.add(
                            MutableStateFlow(
                                SelfEmptyingViewModel.Robot(
                                    robotAccount = user.account_online,
                                    robotName = user.nickname_online,
                                    robotX = (0..300).random().dp,
                                    robotY = (100..500).random().dp
                                )
                            )
                        )
                        selfEmptyingViewModel.updateScreen(selfEmptyingViewModel.user)
                        Log.d(TAG, "add over")
                    } else if (user.type_online == 2) {
                        for (robot in selfEmptyingViewModel.robotList) {
                            if (robot.value.robotAccount == user.account_online) {
                                robot.value.robotMessage = user.message_online
                                runBlocking {
                                    with(selfEmptyingViewModel) {
                                        show(robot)
                                        //when (user.message_online) {
//                                            "Swell", "swell", "我膨胀了" -> {
//                                                runBlocking {
//                                                    robot.value.robotMessage =
//                                                        App.context.resources.getString(R.string.i_am_swelling)
//                                                    selfScale.animateTo(
//                                                        1.2f,
//                                                        animationSpec = tween(1000)
//                                                    )
//                                                    selfScale.animateTo(
//                                                        1f,
//                                                        animationSpec = tween(1000)
//                                                    )
//                                                    selfText = ""
//                                                    selfEmptyingViewModel.hide(
//                                                        selfEmptyingViewModel.user
//                                                    )
//                                                }
//                                            }
//                                            "Wink", "wink", "眨眼" -> {
//                                                runBlocking {
//                                                    robot.value.robotMessage =
//                                                        App.context.resources.getString(R.string.i_am_winking)
//                                                }
//                                            }

                                        //else -> {
                                        selfEmptyingViewModel.operateUserOnline(
                                            user.message_online,
                                            robot
                                        )
                                        //}
                                        //}
                                        delay(2000)
                                        hide(robot)
                                    }
                                }
                            }
                        }
                    } else if (user.type_online == 1) {
                        for (robot in selfEmptyingViewModel.robotList) {
                            if (robot.value.robotAccount == user.account_online) {
                                selfEmptyingViewModel.robotList.remove(robot)
                                selfEmptyingViewModel.updateScreen(selfEmptyingViewModel.user)
                            }
                        }
                    }
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket 连接已关闭")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.d(TAG, "WebSocket 连接失败：${t.message}")
            }
        })
    }

    fun webSocketDisconnect() {
        webSocket.close(1000, "close")
    }

    fun webSocketSendMessage(account: String, message: String) {
        val user = EncodeUser(
            UserOnline(
                account_online = account,
                message_online = message,
                type_online = 1
            )
        )
        webSocket.send(user)
    }

}

