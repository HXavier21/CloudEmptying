package com.example.wintercamp.questionnaire

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    data class QuizViewState(
        val index: Int = 0,
        val questions: List<Question> = obj
    )

    val mutableStateFlow = MutableStateFlow(QuizViewState())
    val stateFlow = mutableStateFlow.asStateFlow()


    fun navigateToNextQuestion() {
        mutableStateFlow.update { it.copy(index = it.index + 1) }
    }

    fun navigateToPreviousQuestion() {
        mutableStateFlow.update { it.copy(index = it.index - 1) }
    }

    fun resumeIndex(){
        mutableStateFlow.update { it.copy(index = 0) }
    }

    fun questionChange(questions: List<Question>) {
        mutableStateFlow.update { it.copy(questions = questions) }
        Log.d(TAG, "questionChange:Start ")
    }

    init {
        Log.d(TAG, "Init")
    }
}