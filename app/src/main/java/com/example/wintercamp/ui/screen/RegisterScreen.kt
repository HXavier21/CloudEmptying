package com.example.wintercamp.ui.screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.wintercamp.ActivityCollector
import com.example.wintercamp.App
import com.example.wintercamp.R
import com.example.wintercamp.data.KvKey
import com.example.wintercamp.network.HttpUtil
import com.example.wintercamp.questionnaire.component.BottomButton
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.component.MyTextField
import com.example.wintercamp.ui.theme.WinterCampTheme
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

@Preview
@Composable
fun RegisterScreen() {
    DisposableEffect(Unit) {

        onDispose {}
    }
    BackHandler {
        ActivityCollector.finishAll()
    }
    WinterCampTheme {
        Surface(Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .scale(1.05f)
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.set_name_background),
                contentDescription = "set_name_background"
            )
            Column {
                Spacer(modifier = Modifier.height(170.dp))
                CustomText(
                    text = stringResource(R.string.your_email),
                    color = Color(51, 51, 51),
                    modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
                )
                MyTextField(
                    height = 70.dp,
                    horizontalPadding = 24.dp,
                    bottomPadding = 15.dp,
                    value = "",
                    onValueChange = {
                    },
                    trailingIcon = {

                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                CustomText(
                    text = "Your Password",
                    color = Color(51, 51, 51),
                    modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
                )
                MyTextField(
                    height = 70.dp,
                    horizontalPadding = 24.dp,
                    bottomPadding = 15.dp,
                    value = "",
                    onValueChange = {},
                    trailingIcon = {

                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                CustomText(
                    text = "Verification Code",
                    color = Color(51, 51, 51),
                    modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
                )
                MyTextField(
                    height = 70.dp,
                    horizontalPadding = 24.dp,
                    bottomPadding = 15.dp,
                    value = "",
                    onValueChange = {},
                    trailingIcon = {

                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                Spacer(modifier = Modifier.height(100.dp))
                Row {
                    BottomButton()
                }
            }
        }
    }
}
