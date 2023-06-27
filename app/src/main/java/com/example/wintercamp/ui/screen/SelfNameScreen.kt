package com.example.wintercamp.ui.screen

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
import com.example.wintercamp.ActivityCollector
import com.example.wintercamp.App
import com.example.wintercamp.MainActivity
import com.example.wintercamp.R
import com.example.wintercamp.data.KvKey
import com.example.wintercamp.data.SelfNameViewModel
import com.example.wintercamp.network.HttpUtil
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.component.MyTextField
import com.example.wintercamp.ui.theme.WinterCampTheme
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

private const val TAG = "SelfNameScreen"

@Preview
@Composable
fun SelfNameScreen(
    selfNameViewModel: SelfNameViewModel = viewModel(),
    onNavigateToSelfEmptyingScreen: () -> Unit = {}
) {
    val kv = MMKV.defaultMMKV()
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    val coroutineScope = rememberCoroutineScope()
    val value by selfNameViewModel.mutableNameFlow.collectAsState()
    var complete by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        kv.decodeString(KvKey.NAME)?.let { it1 -> selfNameViewModel.init(it1) }
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
                Spacer(modifier = Modifier.height(80.dp))
                CustomText(
                    text = stringResource(R.string.your_name),
                    color = Color(51, 51, 51),
                    modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
                )
                MyTextField(
                    horizontalPadding = 24.dp,
                    value = value,
                    onValueChange = { s ->
                        selfNameViewModel.mutableNameFlow.update {
                            s
                        }
                    },
                    trailingIcon = {
                        CustomText(
                            text = "${value.length}/26",
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 20.dp, bottom = 3.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.set_name_button),
                        contentDescription = "set_name_button",
                        modifier = Modifier
                            .scale(1.5f)
                            .clip(MaterialTheme.shapes.extraLarge)
                            .clickable {
                                if (value.length == 0)
                                    Toast
                                        .makeText(
                                            context,
                                            "Please input something",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                else if (value.length > 26)
                                    Toast
                                        .makeText(
                                            context,
                                            "That's too long",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                else
                                    coroutineScope.launch {
                                        HttpUtil.sendOkHttpRequest(
                                            "http://192.168.105.157:11454/users",
                                            object :
                                                Callback {
                                                override fun onFailure(call: Call, e: IOException) {
                                                    Log.d(TAG, e.toString())
                                                }

                                                override fun onResponse(
                                                    call: Call,
                                                    response: Response
                                                ) {
                                                    val responseData = response.body?.toString()
                                                    Log.d(TAG, responseData.toString())
                                                }

                                            })
                                        kv.encode(KvKey.NAME, value)
                                        complete = true
                                        delay(1500)
                                        onNavigateToSelfEmptyingScreen()
                                        delay(500)
                                        complete = false
                                    }
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "arrow_forward",
                        modifier = Modifier.scale(1.5f)
                    )
                }
                Spacer(
                    modifier = Modifier.height(if (complete) 163.dp else 180.dp)
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(2.7f),
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context).data
                            (
                            data = if (!complete) R.drawable.initial_name_set
                            else R.drawable.name_set_completed
                        )
                            .apply(block = {
                                size(Size.ORIGINAL)
                            }).build(), imageLoader = imageLoader
                    ),
                    contentDescription = null
                )
            }
        }
    }
}
