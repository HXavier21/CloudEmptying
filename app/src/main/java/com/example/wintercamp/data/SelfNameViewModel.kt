package com.example.wintercamp.data

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "SelfNameViewModel"

class SelfNameViewModel:ViewModel() {
    val name:String = ""

    val mutableNameFlow = MutableStateFlow(name)

    fun init(name:String){
        mutableNameFlow.update { name }
    }

    init {
        Log.d(TAG, "init")
    }

}