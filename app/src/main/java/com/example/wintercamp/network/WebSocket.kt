package com.example.wintercamp.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

private const val TAG = "WebSocket"

fun WebSocket() {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("http://192.168.84.157:11454") // 使用模拟的 WebSocket 服务器地址
        .build()

    val webSocket = client.newWebSocket(request, object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d(TAG, "WebSocket 连接已打开")

            // 连接成功后，可以发送消息
            webSocket.send("Hello, Sir!")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d(TAG, "收到服务器消息：$text")

            // 在这里处理接收到的消息
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(TAG, "WebSocket 连接已关闭")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.d(TAG, "WebSocket 连接失败：${t.message}")
        }
    })
}


