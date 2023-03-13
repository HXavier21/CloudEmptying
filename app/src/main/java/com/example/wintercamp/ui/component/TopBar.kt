package com.example.wintercamp.ui.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wintercamp.R
import com.example.wintercamp.data.DEFAULT_REPEAT_TIME
import com.example.wintercamp.data.DEFAULt_REPEAT_DURATION
import com.example.wintercamp.data.DeepBreathViewModel
import com.example.wintercamp.data.SelfEmptyingViewModel
import com.example.wintercamp.data.WoodenFishViewModel
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.navigate.RouteName
import com.example.wintercamp.ui.theme.WinterCampTheme
import com.skydoves.cloudy.Cloudy
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.flow.update


private const val TAG = "TopBar"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    deepBreathViewModel: DeepBreathViewModel = viewModel(),
    woodenFishViewModel: WoodenFishViewModel = viewModel(),
    selfEmptyingViewModel: SelfEmptyingViewModel = viewModel(),
    screen: String? = "",
    onNavigateToQuestionnaire: () -> Unit = {}
) {
    val kv = MMKV.defaultMMKV()
    val context = LocalContext.current
    val waveClick = remember { mutableStateOf(false) }
    val musicClick = remember { mutableStateOf(false) }
    WinterCampTheme {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(45.dp))
            Image(
                painter = painterResource(id = R.drawable.music),
                contentDescription = "music_controller",
                modifier = Modifier
                    .scale(3.2f)
                    .padding(top = 20.dp, end = 20.dp, start = 20.dp, bottom = 17.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable {
                        if (musicClick.value) {
                            when (screen) {
                                RouteName.Self_Emptying_Screen -> {
                                    selfEmptyingViewModel.startPlaying()
                                }

                                RouteName.Wooden_Fish_Screen -> {
                                    woodenFishViewModel.resumeVolume()
                                }

                                RouteName.Breathing_Screen -> {
                                    deepBreathViewModel.startPlaying()
                                }
                            }
                            musicClick.value = false
                        } else {
                            when (screen) {
                                RouteName.Self_Emptying_Screen -> {
                                    selfEmptyingViewModel.stopPlaying()
                                }

                                RouteName.Wooden_Fish_Screen -> {
                                    woodenFishViewModel.silent()
                                }

                                RouteName.Breathing_Screen -> {
                                    deepBreathViewModel.stopPlaying()
                                }
                            }
                            musicClick.value = true
                        }
                    }
            )
            Image(painter = painterResource(id = R.drawable.wave),
                contentDescription = "function_button",
                modifier = Modifier
                    .scale(3.2f)
                    .padding(20.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable {
                        waveClick.value = !waveClick.value
                    }
            )
            if (waveClick.value) {
                when (screen) {
                    RouteName.Self_Emptying_Screen -> {
                        onNavigateToQuestionnaire()
                        waveClick.value = false
                    }

                    RouteName.Wooden_Fish_Screen -> {
                        woodenFishViewModel.resume()
                        waveClick.value = false
                    }

                    RouteName.Breathing_Screen -> {
                        Box(
                            modifier = Modifier.offset(115.dp),
                        ) {
                            Cloudy(radius = 25) {
                                Image(
                                    painter = painterResource(id = R.drawable.wave_click),
                                    contentDescription = "wave_click",
                                    alpha = 0.4f
                                )
                            }
                            Column(
                                modifier = Modifier.offset(10.dp)
                            ) {
                                var duration by rememberSaveable {
                                    mutableStateOf(
                                        kv.decodeLong(
                                            "duration", DEFAULt_REPEAT_DURATION
                                        ).toString()
                                    )
                                }
                                var times by rememberSaveable {
                                    mutableStateOf(
                                        kv.decodeInt(
                                            "times", DEFAULT_REPEAT_TIME
                                        ).toString()
                                    )
                                }
                                CustomText(text = "Duration&Times")
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.offset(y = -15.dp)
                                ) {
                                    TextField(
                                        colors = TextFieldDefaults.textFieldColors(
                                            containerColor = Color.White,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent
                                        ),
                                        value = duration,
                                        onValueChange = {
                                            if (it.isDigitsOnly()) duration = it
                                        },
                                        modifier = Modifier
                                            .scale(0.4f)
                                            .width(250.dp)
                                            .offset(-190.dp)
                                            .clip(MaterialTheme.shapes.extraLarge)
                                    )
                                    CustomText(
                                        text = "ms",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                        modifier = Modifier.offset(-150.dp)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.offset(y = -40.dp)
                                ) {
                                    TextField(
                                        colors = TextFieldDefaults.textFieldColors(
                                            containerColor = Color.White,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent
                                        ),
                                        value = times,
                                        onValueChange = { if (it.isDigitsOnly()) times = it },
                                        modifier = Modifier
                                            .scale(0.4f)
                                            .width(250.dp)
                                            .offset(-190.dp)
                                            .clip(MaterialTheme.shapes.extraLarge)
                                    )
                                    CustomText(
                                        text = "times",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                        modifier = Modifier.offset(-150.dp)
                                    )
                                }
                                Row(modifier = Modifier.offset(y = -45.dp)) {
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Box(contentAlignment = Alignment.Center) {
                                        Image(painter = painterResource(id = R.drawable.reset),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .scale(1.2f)
                                                .clickable {
                                                    kv.encode("duration", 1000)
                                                    kv.encode("times", 10)
                                                    duration = "1000"
                                                    times = "10"
                                                    deepBreathViewModel.resume()
                                                })
                                        CustomText(
                                            text = "Reset",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Box(contentAlignment = Alignment.Center) {
                                        Image(painter = painterResource(id = R.drawable.done),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .scale(1.2f)
                                                .clickable {
                                                    duration
                                                        .toIntOrNull()
                                                        ?.let { kv.encode("duration", it) }
                                                        ?: Toast
                                                            .makeText(
                                                                context,
                                                                "Null",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    times
                                                        .toIntOrNull()
                                                        ?.let { kv.encode("times", it) }
                                                        ?: Toast
                                                            .makeText(
                                                                context,
                                                                "Null",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    deepBreathViewModel.init(
                                                        kv.decodeInt(
                                                            "times",
                                                            DEFAULT_REPEAT_TIME
                                                        ),
                                                        kv.decodeLong(
                                                            "duration",
                                                            DEFAULt_REPEAT_DURATION
                                                        )
                                                    )
                                                    waveClick.value = false
                                                })
                                        CustomText(
                                            text = "Done",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TopButtonPreview() {
    TopBar()
}
