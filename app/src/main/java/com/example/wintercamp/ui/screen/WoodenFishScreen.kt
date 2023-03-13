package com.example.wintercamp.ui.screen

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wintercamp.R
import com.example.wintercamp.data.KvKey
import com.example.wintercamp.data.WoodenFishViewModel
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.component.TopBar
import com.example.wintercamp.ui.layout.CircleOnClick
import com.example.wintercamp.ui.navigate.RouteName
import com.example.wintercamp.ui.theme.WinterCampTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "WoodenFishScreen"

@Composable
fun WoodenFishScreen(
    woodenFishViewModel: WoodenFishViewModel = viewModel(),
    onNavigateToBreathScreen: () -> Unit = {},
    onNavigateToSelfEmptying: () -> Unit = {}
) {
    val kv = MMKV.defaultMMKV()
    val viewState by woodenFishViewModel.stateFlow.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var knock by remember { mutableStateOf(false) }
    val scale = remember {
        androidx.compose.animation.core.Animatable(3f)
    }
    val cloudScale = remember {
        androidx.compose.animation.core.Animatable(1f)
    }
    var circleView: CircleOnClick? = null
    BackHandler {
        kv.encode(KvKey.MERIT, viewState.num)
        onNavigateToSelfEmptying()
    }
    WinterCampTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.wooden_fish_screen),
                contentDescription = "wooden_fish_background",
                modifier = Modifier
                    .scale(1.05f)
                    .fillMaxSize()
            )
            AndroidView(
                factory = {
                    LayoutInflater
                        .from(it)
                        .inflate(R.layout.circle_view, null)
                        .apply {
                            woodenFishViewModel.initView(
                                kv.decodeInt(KvKey.MERIT)
                            )
                        }
                },
                update = {
                    circleView = it.findViewById(R.id.circleview)
                }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "ArrowBack",
                        modifier = Modifier
                            .padding(start = 20.dp, top = 50.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) { onNavigateToSelfEmptying() }
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cloud1),
                        contentDescription = "cloud1",
                        modifier = Modifier.scale(2.8f)
                    )
                    Column {
                        CustomText(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.merit),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.displayMedium
                        )
                        CustomText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -5.dp),
                            text = viewState.num.toString(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(2f))
                Image(
                    painter = painterResource(
                        id = if (knock) R.drawable.knocked
                        else R.drawable.knock
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .offset((-5).dp)
                        .weight(1f)
                        .scale(scale = scale.value)
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ) {
                            circleView!!.show()
                            woodenFishViewModel.startPlaying()
                            coroutineScope.launch {
                                launch {
                                    woodenFishViewModel.plusOne()
                                }
                                launch {
                                    knock = true
                                    scale.animateTo(2.8f)
                                    delay(100)
                                    knock = false
                                    scale.animateTo(3f)
                                }
                            }
                        }
                )
                Spacer(modifier = Modifier.weight(2f))
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .offset((-85).dp)
                            .scale(2.8f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cloud2),
                            contentDescription = "cloud2",
                            modifier = Modifier
                                .scale(cloudScale.value)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    coroutineScope.launch {
                                        cloudScale.animateTo(
                                            1.1f,
                                            tween(200)
                                        )
                                        cloudScale.animateTo(1f)
                                    }
                                    kv.encode(KvKey.MERIT, viewState.num)
                                    onNavigateToBreathScreen()
                                }
                        )
                        Row(
                            modifier = Modifier
                                .scale(0.5f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomText(
                                text = stringResource(R.string.deep_breath),
                                maxLines = 2,
                                lineHeight = 18.sp
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "arrow_forward"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            TopBar(screen = RouteName.Wooden_Fish_Screen)
        }
    }
}


@Preview
@Composable
fun WoodenFishScreenPreview() {
    WoodenFishScreen()
}