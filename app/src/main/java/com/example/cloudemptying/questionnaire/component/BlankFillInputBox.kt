package com.example.cloudemptying.questionnaire.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.cloudemptying.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlankFillInputBox(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { CustomText(stringResource(R.string.answer)) },
        modifier = modifier
    )
}
