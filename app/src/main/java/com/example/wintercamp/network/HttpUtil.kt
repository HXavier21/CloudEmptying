package com.example.wintercamp.network

import android.util.Log
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

private const val TAG = "HttpUtil"

object HttpUtil {
    fun sendOkHttpRequest(address: String, callback: Callback) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback)
    }

    fun sendOkHttpPostRequest(address: String, callback: Callback, requestBody: RequestBody) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(callback)
    }
}
