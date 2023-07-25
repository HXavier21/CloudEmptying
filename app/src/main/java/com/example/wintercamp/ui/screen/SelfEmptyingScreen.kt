package com.example.wintercamp.ui.screen

import android.util.Log
import android.view.LayoutInflater
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wintercamp.ActivityCollector
import com.example.wintercamp.App
import com.example.wintercamp.App.Companion.context
import com.example.wintercamp.R
import com.example.wintercamp.data.KvKey
import com.example.wintercamp.data.Operation
import com.example.wintercamp.data.SelfEmptyingViewModel
import com.example.wintercamp.network.WebSocket
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.component.MyTextField
import com.example.wintercamp.ui.component.SelfEmptyingItem
import com.example.wintercamp.ui.component.TopBar
import com.example.wintercamp.ui.navigate.RouteName
import com.example.wintercamp.ui.theme.WinterCampTheme
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SelfEmptyingScreen"

@Composable
fun SelfEmptyingScreen(
    selfEmptyingViewModel: SelfEmptyingViewModel = viewModel(),
    onNavigateToWoodenFishScreen: () -> Unit = {},
    onNavigateToQuestionnaire: () -> Unit = {},
    onNavigateToHiddenScreen: () -> Unit = {}
) {
    val kv = MMKV.defaultMMKV()
    val coroutineScope = rememberCoroutineScope()
    var selfText by rememberSaveable { mutableStateOf("") }
    val stateFlows = selfEmptyingViewModel.robotList
    val animationSpec = tween<Dp>(2000)
    val user by selfEmptyingViewModel.user.collectAsState()
    var randomRobot: Int = -1
    val selfScale = remember {
        androidx.compose.animation.core.Animatable(1f)
    }

    DisposableEffect(Unit) {
        selfEmptyingViewModel.run {
            startPlaying()
            if (App.guest_mode) {
                initAll("Guest")
            } else {
                kv.decodeString(KvKey.ACCOUNT)
                    ?.let { WebSocket.webSocketConnect(it, selfEmptyingViewModel) }
                initAll(kv.decodeString(KvKey.NAME) ?: KvKey.NAME)
            }
        }
        onDispose {
            selfEmptyingViewModel.stopPlaying()
        }
    }
    BackHandler {
        if (!App.guest_mode) WebSocket.webSocketDisconnect()
        ActivityCollector.finishAll()
    }
    WinterCampTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.emptying_background),
                contentDescription = "emptying_background",
                modifier = Modifier
                    .scale(1.05f)
                    .fillMaxSize()
            )
            Column {
                val userX by animateDpAsState(targetValue = user.robotX, animationSpec)
                val userY by animateDpAsState(targetValue = user.robotY, animationSpec)
                Box(modifier = Modifier.weight(1f)) {
                    for (stateFlow in stateFlows) {
                        stateFlow.collectAsState().value.run {
                            val xdp by animateDpAsState(targetValue = robotX, animationSpec)
                            val ydp by animateDpAsState(targetValue = robotY, animationSpec)
                            SelfEmptyingItem(
                                name = robotName,
                                visible = robotVisible,
                                text = robotMessage,
                                modifier = Modifier
                                    .offset(xdp, ydp)
                                    .clickable(
                                        enabled = (robotName == "Xavier Hugo"),
                                        indication = null,
                                        interactionSource = remember {
                                            MutableInteractionSource()
                                        }
                                    ) {
                                        onNavigateToHiddenScreen()
                                    }
                            )
                        }
                    }
                    SelfEmptyingItem(
                        modifier = Modifier
                            .offset(userX, userY)
                            .scale(selfScale.value)
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                coroutineScope.launch {
                                    selfScale.animateTo(
                                        1.1f,
                                        tween(1000)
                                    )
                                    selfScale.animateTo(1f)
                                }
                                if (!App.guest_mode) WebSocket.webSocketDisconnect()
                                onNavigateToWoodenFishScreen()
                            },
                        visible = user.robotVisible,
                        text = selfText,
                        name = user.robotName,
                        image = when (selfText) {
                            stringResource(R.string.i_am_winking) -> R.drawable.wink
                            else -> R.drawable.self_emptying
                        }
                    )
                }
                CustomText(
                    modifier = Modifier.padding(start = 20.dp, bottom = 5.dp),
                    text = stringResource(R.string.move_up_down_left_right_swell_wink),
                    color = Color.Gray
                )
                MyTextField(
                    horizontalPadding = 16.dp,
                    selfCalled = user.robotVisible,
                    value = selfText,
                    onValueChange = { selfText = it },
                    content = if (!user.robotVisible) stringResource(R.string.input_to_find_yourself)
                    else stringResource(R.string.find_it_try_to_poke),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.emptying_send_button),
                            contentDescription = "send_instruction",
                            modifier = Modifier
                                .scale(1.3f)
                                .padding(end = 20.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .clickable {
                                    coroutineScope.launch {
                                        if (selfText != "") {
                                            if (!App.guest_mode) {
                                                kv
                                                    .decodeString(KvKey.ACCOUNT)
                                                    ?.let {
                                                        WebSocket.webSocketSendMessage(
                                                            it,
                                                            selfText
                                                        )
                                                    }
                                            }
                                            selfEmptyingViewModel.show(selfEmptyingViewModel.user)
                                            when (selfText) {
                                                "Swell", "swell", "我膨胀了" -> {
                                                    coroutineScope.launch {
                                                        selfText =
                                                            context.resources.getString(R.string.i_am_swelling)
                                                        selfScale.animateTo(
                                                            1.2f,
                                                            animationSpec = tween(1000)
                                                        )
                                                        selfScale.animateTo(
                                                            1f,
                                                            animationSpec = tween(1000)
                                                        )
                                                        selfText = ""
                                                        selfEmptyingViewModel.hide(
                                                            selfEmptyingViewModel.user
                                                        )
                                                    }
                                                }

                                                "Wink", "wink", "眨眼" -> {
                                                    coroutineScope.launch {
                                                        selfText =
                                                            context.resources.getString(R.string.i_am_winking)
                                                        delay(3000)
                                                        selfText = ""
                                                        selfEmptyingViewModel.hide(
                                                            selfEmptyingViewModel.user
                                                        )
                                                    }
                                                }

                                                else -> {
                                                    coroutineScope.launch {
                                                        selfEmptyingViewModel.userOperation(
                                                            selfText.toOperation()
                                                        )
                                                        delay(2000)
                                                        selfText = ""
                                                        selfEmptyingViewModel.hide(
                                                            selfEmptyingViewModel.user
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        delay(3000)
                                        coroutineScope.launch {
                                            with(selfEmptyingViewModel) {
                                                randomRobot = (0..2).random()
                                                show(robotList[randomRobot])
                                                randomOperation(randomRobot = robotList[randomRobot])
                                                delay(2000)
                                                hide(robotList[randomRobot])
                                            }
                                        }
                                    }
                                }
                        )
                        CustomText(
                            modifier = Modifier.padding(end = 24.dp),
                            text = stringResource(R.string.send),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = {
                LayoutInflater
                    .from(it)
                    .inflate(R.layout.text_clock, null)
            },
            modifier = Modifier.offset(y = 50.dp)
        )
    }
    Row {
        Spacer(modifier = Modifier.weight(1f))
        TopBar(
            screen = RouteName.Self_Emptying_Screen,
            onNavigateToQuestionnaire = onNavigateToQuestionnaire
        )
    }
}

@Preview
@Composable
fun SelfEmptyingScreenPreview() {
    SelfEmptyingScreen()
}

fun String.toOperation(): Operation {
    return when (this) {
        "Move", "move", "移动" -> Operation.Move
        "Up", "up", "向上" -> Operation.Up
        "Down", "down", "向下" -> Operation.Down
        "Left", "left", "向左" -> Operation.Left
        "Right", "right", "向右" -> Operation.Right
        else -> Operation.Welcome
    }
}