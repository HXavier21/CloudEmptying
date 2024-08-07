package com.example.cloudemptying.ui.screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.cloudemptying.ActivityCollector
import com.example.cloudemptying.App
import com.example.cloudemptying.App.Companion.context
import com.example.cloudemptying.R
import com.example.cloudemptying.data.KvKey
import com.example.cloudemptying.network.HttpUtil
import com.example.cloudemptying.network.ServerPath
import com.example.cloudemptying.questionnaire.component.CustomText
import com.example.cloudemptying.ui.component.MyLabelTextField
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

private const val TAG = "LogInScreen"

@Preview
@Composable
fun LogInScreen(
    onNavigateToEmptying: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    val kv = MMKV.defaultMMKV()
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var complete by remember { mutableStateOf(false) }
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    DisposableEffect(Unit) {
        kv.decodeString(KvKey.ACCOUNT)?.let { email = it }
        onDispose {
        }
    }
    BackHandler {
        ActivityCollector.finishAll()
    }
    Surface(
        Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Image(
            painter = painterResource(id = R.drawable.log_in),
            contentDescription = "emptying_background",
            modifier = Modifier
                .scale(1.1f)
                .fillMaxWidth()
        )
        Column {
            Spacer(modifier = Modifier.height(100.dp))
            CustomText(
                text = "Sign in",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(start = 20.dp, bottom = 30.dp)
            )
            MyLabelTextField(
                horizontalPadding = 24.dp,
                value = email,
                onValueChange = {
                    email = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                label = {
                    CustomText(text = "E-mail")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            MyLabelTextField(
                horizontalPadding = 24.dp,
                value = password,
                onValueChange = {
                    password = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                label = {
                    CustomText(text = "password")
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(172, 204, 248)
                ),
                onClick = {
                    runBlocking {
                        var responseData: String? = null
                        HttpUtil.sendOkHttpPostRequest(
                            "${ServerPath.httpPath}/login",
                            requestBody = FormBody
                                .Builder()
                                .add("account", email)
                                .add("password", password)
                                .build(),
                            callback = object : Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    Log.d(TAG, e.toString())
                                }

                                override fun onResponse(
                                    call: Call,
                                    response: Response
                                ) {
                                    responseData = response.body?.string()
                                    responseData?.let { Log.d(TAG, it) }
                                }
                            }
                        )
                        coroutineScope.launch {
                            complete = !complete
                            delay(1500)
                            try {
                                val jsonObject = responseData?.let { JSONObject(it) }
                                if (jsonObject != null) {
                                    Log.d(TAG, jsonObject.getString("nickname"))
                                    kv.encode(KvKey.ACCOUNT, email)
                                    kv.encode(KvKey.PASSWORD, password)
                                    kv.encode(KvKey.NAME, jsonObject.getString("nickname"))
                                }
                                onNavigateToEmptying()
                            } catch (e: Exception) {
                                Log.d(TAG, e.toString())
                                complete = !complete
                                Toast.makeText(
                                    context,
                                    "Invalid account or password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            ) {
                CustomText(
                    text = "Sign in",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomText(
                    text = "Don't have an account? ",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                CustomText(
                    text = "Sign up",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    ) {
                        onNavigateToRegister()
                    }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomText(
                    text = "Or try guest mode first",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    ) {
                        App.guest_mode = true
                        onNavigateToEmptying()
                    }
                )
            }
            Spacer(
                modifier = Modifier.height(if (complete) 103.dp else 120.dp)
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