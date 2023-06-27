package com.example.wintercamp.network

import android.util.Log
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

private const val TAG = "HttpUtil"

sealed class RequestMode(
    val POST: RequestMode
)

object HttpUtil {
    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback)
        Log.d(TAG, "init")
    }

    fun sendOkHttpPostRequest(address: String,callback: Callback,){
    }
}
