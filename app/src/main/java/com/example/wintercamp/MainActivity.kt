package com.example.wintercamp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.wintercamp.App.Companion.context
import com.example.wintercamp.network.WebSocket
import com.example.wintercamp.ui.navigate.MyNavigation

val LocalImageLoader = staticCompositionLocalOf {
    ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }

        setContent {
            MyNavigation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WebSocket.webSocketDisconnect()
        ActivityCollector.removeActivity(this)
    }

}