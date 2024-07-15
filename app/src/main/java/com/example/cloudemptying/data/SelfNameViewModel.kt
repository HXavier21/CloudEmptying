package com.example.cloudemptying.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cloudemptying.network.HttpUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Response
import java.io.IOException

private const val TAG = "SelfNameViewModel"

class SelfNameViewModel:ViewModel() {
    val name:String = ""

    val mutableNameFlow = MutableStateFlow(name)

    fun init(name:String){
        mutableNameFlow.update { name }
    }

    fun sentNameRequest(){
        HttpUtil.sendOkHttpPostRequest(
            "http://192.168.84.157:11455/users",
            requestBody = FormBody
                .Builder()
                .add("account", "1585375420@qq.com")
                .add("password","123456")
                .build(),
            callback = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, e.toString())
                }

                override fun onResponse(
                    call: Call,
                    response: Response
                ) {
                    val responseData = response.body?.string()
                    if (responseData != null) {
                        Log.d(TAG, responseData)
                    }
                }
            }
        )
    }
}