package com.example.wintercamp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.tencent.mmkv.MMKV

private const val TAG = "App"

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val rootDir = MMKV.initialize(this)
        context = this.applicationContext
    }

    companion object {
        var guest_mode = false
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}
