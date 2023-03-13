package com.example.wintercamp.questionnaire

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
sealed class Question {
    abstract val headline: String
}

@Serializable
data class SingleChoice(
    override val headline: String,
    val options: List<String>,
    var selectedItem: Int = -1
) : Question() {
    @Transient
    val selected: MutableState<Int> = mutableStateOf(-1)
}

@Serializable
data class MultipleChoice(
    override val headline: String,
    val options: List<String>,
    val checkedItems: MutableList<Int> = mutableListOf()
) : Question() {
    @Transient
    val checked: SnapshotStateList<Int> = mutableStateListOf()
}

@Serializable
data class BlankFill(
    override val headline: String,
    var answer: String = ""
) : Question() {
    @Transient
    val text: MutableState<String> = mutableStateOf("")
}

val options1:List<String> = listOf(
    "1","2","3","4","5","6","7","8","9","10"
)


val options2: List<String> = listOf(
    "From friends",
    "From the developer",
    "From the Internet",
    "From the Github",
    "From relatives",
    "By myself",
    "From the propaganda"
)
val question1: SingleChoice = SingleChoice(
    "How would you rate this app?",
    options1
)
val question2: MultipleChoice = MultipleChoice(
    "How did you learn about the app?",
    options2
)
val question3: BlankFill = BlankFill(
    "Please briefly talk about your advice or comment on the app."
)
val obj: List<Question> = listOf(question1, question2, question3)