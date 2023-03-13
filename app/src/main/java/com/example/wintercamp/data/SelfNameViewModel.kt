package com.example.wintercamp.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SelfNameViewModel:ViewModel() {
    val name:String = ""

    val mutableNameFlow = MutableStateFlow(name)

    fun init(name:String){
        mutableNameFlow.update { name }
    }


}