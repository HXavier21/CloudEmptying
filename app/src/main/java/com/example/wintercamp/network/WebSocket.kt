package com.example.wintercamp.network

import android.util.Log
import com.example.wintercamp.questionnaire.EncodeUser
import com.example.wintercamp.questionnaire.UserOnline
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

private const val TAG = "WebSocket"

object WebSocket{

    lateinit var webSocket:WebSocket

    fun webSocketConnect(account:String= "") {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(ServerPath.wsPath)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket 连接已打开")
                val user = EncodeUser(UserOnline(account = account, type = 0))
                Log.d(TAG, user)
                webSocket.send(user)
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

    fun webSocketDisconnect(){
        webSocket.close(1000,"close")
    }


}

