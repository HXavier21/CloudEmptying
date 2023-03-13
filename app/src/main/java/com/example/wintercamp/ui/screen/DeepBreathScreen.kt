package com.example.wintercamp.ui.screen

import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.wintercamp.App
import com.example.wintercamp.App.Companion.context
import com.example.wintercamp.R
import com.example.wintercamp.data.DEFAULT_REPEAT_TIME
import com.example.wintercamp.data.DEFAULt_REPEAT_DURATION
import com.example.wintercamp.data.DeepBreathViewModel
import com.example.wintercamp.data.KvKey
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.component.TopBar
import com.example.wintercamp.ui.navigate.RouteName
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "DeepBreathScreen"

@Preview
@Composable
fun DeepBreathScreen(
    deepBreathViewModel: DeepBreathViewModel = viewModel(),
    onNavigateToWoodenFish: () -> Unit = {}
) {
    val kv = MMKV.defaultMMKV()
    val duration by deepBreathViewModel.mutableRepeatDurationFlow.collectAsState()
    val times by deepBreathViewModel.mutableRepeatTimesFlow.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val breathCloudScale = remember {
        androidx.compose.animation.core.Animatable(1f)
    }
    val returnCloudScale = remember {
        androidx.compose.animation.core.Animatable(1f)
    }
    var breathText by rememberSaveable { mutableStateOf(context.resources.getString(R.string.start)) }
    var x by remember { mutableStateOf(-1.dp) }
    var y by remember { mutableStateOf(6.dp) }
    val headX by animateDpAsState(
        targetValue = x,
        animationSpec = tween(3000)
    )
    val headY by animateDpAsState(
        targetValue = y,
        animationSpec = tween(3000)
    )
    var inhale by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    DisposableEffect(Unit) {
        deepBreathViewModel.run {
            startPlaying()
            init(
                kv.decodeInt(KvKey.TIMES, DEFAULT_REPEAT_TIME),
                kv.decodeLong(KvKey.DURATION, DEFAULt_REPEAT_DURATION)
            )
        }

        onDispose {
            deepBreathViewModel.stopPlaying()
        }
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.deep_breath_background),
                contentDescription = "deep_breath_background",
                modifier = Modifier.scale(1.05f)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .scale(2.8f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.breath_cloud),
                        contentDescription = "breath_cloud",
                        modifier = Modifier
                            .scale(breathCloudScale.value)
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                coroutineScope.launch {
                                    breathCloudScale.animateTo(
                                        0.9f,
                                        tween(200)
                                    )
                                    breathCloudScale.animateTo(1f)
                                    breathText = "3"
                                    delay(1000)
                                    breathText = "2"
                                    delay(1000)
                                    breathText = "1"
                                    delay(1000)
                                    try {
                                        repeat(times) {
                                            inhale = true
                                            breathText =
                                                App.context.resources.getString(R.string.inhale)
                                            x = 0.dp
                                            y = 0.dp
                                            delay(3000 + duration)
                                            x = -1.dp
                                            y = 6.dp
                                            inhale = false
                                            breathText =
                                                App.context.resources.getString(R.string.exhale)
                                            delay(3000 + duration)
                                        }
                                        breathText = App.context.resources.getString(R.string.end)
                                        delay(2000)
                                        breathText = App.context.resources.getString(R.string.start)
                                    } catch (e: Exception) {
                                        breathText = App.context.resources.getString(R.string.start)
                                        Toast
                                            .makeText(context, "NaN", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                    )
                    CustomText(text = breathText)
                }
                Spacer(modifier = Modifier.weight(2.5f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .scale(3f)
                ) {
                    if (inhale) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(context).data(data = R.drawable.flower)
                                    .apply(block = {
                                        size(Size.ORIGINAL)
                                    }).build(), imageLoader = imageLoader
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .scale(0.3f)
                                .offset(-80.dp)
                        )
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(context).data(data = R.drawable.flower)
                                    .apply(block = {
                                        size(Size.ORIGINAL)
                                    }).build(), imageLoader = imageLoader
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .scale(0.3f)
                                .offset(40.dp, -100.dp)
                        )
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(context).data(data = R.drawable.flower)
                                    .apply(block = {
                                        size(Size.ORIGINAL)
                                    }).build(), imageLoader = imageLoader
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .scale(0.3f)
                                .offset(180.dp, 0.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.leg),
                        contentDescription = "leg"
                    )
                    Image(
                        painter = painterResource(
                            id =
                            if (inhale) R.drawable.head
                            else R.drawable.head_eyes_closed
                        ),
                        contentDescription = "head",
                        modifier = Modifier.offset(headX, headY)
                    )
                }
                Spacer(modifier = Modifier.weight(1.5f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .scale(2.8f)
                            .offset(22.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.breath_return_cloud),
                            contentDescription = "breath_return_cloud",
                            modifier = Modifier
                                .scale(returnCloudScale.value)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    coroutineScope.launch {
                                        returnCloudScale.animateTo(
                                            1.1f,
                                            tween(200)
                                        )
                                        returnCloudScale.animateTo(1f)
                                    }
                                    onNavigateToWoodenFish()
                                }
                        )
                        Row(
                            modifier = Modifier.scale(0.6f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "breath_return",
                                modifier = Modifier.scale(0.9f)
                            )
                            CustomText(
                                text = stringResource(R.string.merit_counter),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            TopBar(screen = RouteName.Breathing_Screen)
        }
    }
}