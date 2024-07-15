package com.example.cloudemptying.questionnaire.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questionnaire.Headline
import com.example.questionnaire.ProgressIndicator
import com.example.cloudemptying.R
import com.example.cloudemptying.questionnaire.BlankFill
import com.example.cloudemptying.questionnaire.MultipleChoice
import com.example.cloudemptying.questionnaire.Question
import com.example.cloudemptying.questionnaire.QuizViewModel
import com.example.cloudemptying.questionnaire.SingleChoice
import com.example.cloudemptying.questionnaire.component.BlankFillInputBox
import com.example.cloudemptying.questionnaire.component.BottomButton
import com.example.cloudemptying.questionnaire.component.MultipleChoiceOptionItem
import com.example.cloudemptying.questionnaire.component.SingleChoiceOptionItem
import com.example.cloudemptying.ui.theme.WinterCampTheme

private const val TAG = "QuizScreen"

@Composable
fun QuizScreen(
    quizViewModel: QuizViewModel = viewModel(),
    onNavigateToEmptying: () -> Unit = {},
    onNavigateToFinish: () -> Unit = {}
) {
    val viewState by quizViewModel.stateFlow.collectAsState()
    Log.d(TAG, viewState.questions.toString())
    viewState.run {
        QuizScreenImpl(
            index = index + 1,
            total = questions.size,
            question = questions[index],
            onNavigateToNext = {
                if (index < questions.size - 1)
                    quizViewModel.navigateToNextQuestion()
                else {
                    for (quetion in questions) {
                        when (quetion) {
                            is MultipleChoice -> {
                                quetion.checkedItems.addAll(quetion.checked.toList())
                            }

                            is SingleChoice -> {
                                quetion.selectedItem = quetion.selected.value
                            }

                            is BlankFill -> {
                                quetion.answer = quetion.text.value
                            }
                        }
                    }
                    onNavigateToFinish()
                }
            },
            onNavigateToPrevious = {
                if (index > 0)
                    quizViewModel.navigateToPreviousQuestion()
                else
                    onNavigateToEmptying()
            }
        )
    }
}

@Composable
fun QuizScreenImpl(
    index: Int = 1,
    total: Int = 3,
    question: Question,
    onNavigateToPrevious: () -> Unit = {},
    onNavigateToNext: () -> Unit = {},
) {
    BackHandler(index != 0) {
        onNavigateToPrevious()
    }
    WinterCampTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxHeight()) {
                ProgressIndicator(section = index, total = total)
                Headline(
                    type = when (question) {
                        is BlankFill -> stringResource(R.string.blank_fill)
                        is MultipleChoice -> stringResource(R.string.multiple_choice)
                        is SingleChoice -> stringResource(R.string.single_choice)
                    }, headline = question.headline
                )
                when (question) {
                    is BlankFill -> {
                        BlankFillInputBox(
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                                .fillMaxWidth()
                                .weight(1f),
                            text = question.text.value,
                            onValueChange = { question.text.value = it }
                        )
                    }

                    is MultipleChoice -> {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                        ) {
                            itemsIndexed(question.options) { index, option ->
                                MultipleChoiceOptionItem(
                                    text = option,
                                    onCheckedChange = {
                                        if (it) question.checked.add(index)
                                        else question.checked.remove(index)
                                    },
                                    checked = question.checked.contains(index),
                                )
                            }
                        }
                    }

                    is SingleChoice -> {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                        ) {
                            itemsIndexed(question.options) { index, option ->
                                SingleChoiceOptionItem(
                                    text = option,
                                    onClick = { question.selected.value = index },
                                    selected = (question.selected.value == index),
                                    index = index
                                )

                            }
                        }
                    }
                }
                BottomButton(onNavigateToPrevious, onNavigateToNext)
            }
        }
    }
}