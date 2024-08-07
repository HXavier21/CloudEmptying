package com.example.cloudemptying.data

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.cloudemptying.App.Companion.context
import com.example.cloudemptying.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "WoodenFishViewModel"

class WoodenFishViewModel : ViewModel() {
    data class WoodenFishState(
        val num: Int = 0
    )

    val mutableStateFlow = MutableStateFlow(WoodenFishState())
    val stateFlow = mutableStateFlow.asStateFlow()

    fun plusOne() {
        mutableStateFlow.update { it.copy(num = it.num + 1) }
    }

    fun resume() {
        mutableStateFlow.update { it.copy(num = 0) }
    }

    fun initView(
        merit: Int
    ) {
        mutableStateFlow.update {
            it.copy(
                num = merit
            )
        }
    }

    val musicplayer: ExoPlayer = ExoPlayer.Builder(context).build()

    fun startPlaying() {
        val uriStr = "android.resource://" +
                context.packageName +
                "/" + R.raw.wooden_fish
        val uri = Uri.parse(uriStr)
        val mediaItem = MediaItem.fromUri(uri)
        musicplayer.setMediaItem(mediaItem)
        musicplayer.prepare()
        musicplayer.play()
    }

    fun silent(){
        musicplayer.volume = 0f
    }

    fun resumeVolume(){
        musicplayer.volume = 1f
    }
}