package com.example.wintercamp.questionnaire

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {
    data class QuizViewState(
        val index: Int = 0,
        val questions: List<Question> = obj,
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

    fun questionInit() {
        mutableStateFlow.update { it.copy(questions = obj) }
    }
}