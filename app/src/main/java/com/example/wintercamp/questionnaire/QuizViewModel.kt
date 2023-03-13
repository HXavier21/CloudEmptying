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
        val questions: List<Question> = emptyList(),
        val topic: String = "Fruit Preference"
    )

    var mutableJsonContentFlow = MutableStateFlow(Encode(obj))

    val mutableStateFlow = MutableStateFlow(QuizViewState())
    val stateFlow = mutableStateFlow.asStateFlow()

    fun topicResume() {
        mutableStateFlow.update { it.copy(topic = "Fruit Preference") }
    }

    fun navigateToNextQuestion() {
        mutableStateFlow.update { it.copy(index = it.index + 1) }
    }

    fun navigateToPreviousQuestion() {
        mutableStateFlow.update { it.copy(index = it.index - 1) }
    }

    fun questionChange(questions: List<Question>) {
        mutableStateFlow.update { it.copy(questions = questions) }
        Log.d(TAG, "questionChange:Start ")
    }
}