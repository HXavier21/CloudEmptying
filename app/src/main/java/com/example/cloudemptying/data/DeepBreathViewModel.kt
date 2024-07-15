package com.example.cloudemptying.data

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.cloudemptying.App
import com.example.cloudemptying.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

const val DEFAULT_REPEAT_TIME = 10
const val DEFAULt_REPEAT_DURATION:Long = 1000

class DeepBreathViewModel : ViewModel() {

    val exoPlayer = ExoPlayer.Builder(App.context).build()

    val mutableRepeatTimesFlow = MutableStateFlow(DEFAULT_REPEAT_TIME)
    val mutableRepeatDurationFlow = MutableStateFlow(DEFAULt_REPEAT_DURATION)

    fun resume() {
        mutableRepeatDurationFlow.update { DEFAULt_REPEAT_DURATION }
        mutableRepeatTimesFlow.update { DEFAULT_REPEAT_TIME }
    }

    fun stopPlaying() {
        exoPlayer.stop()
    }

    fun startPlaying() {
        val uriStr1 = """android.resource://${App.context.packageName}/${R.raw.deep_breath_bgm}"""
        val uri1 = Uri.parse(uriStr1)
        exoPlayer.setMediaItem(MediaItem.fromUri(uri1))
        exoPlayer.prepare()
        exoPlayer.repeatMode = REPEAT_MODE_ALL
        exoPlayer.play()
    }

    fun init(
        times: Int,
        duration: Long
    ) {
        mutableRepeatDurationFlow.update { duration }
        mutableRepeatTimesFlow.update { times}
    }
}