package com.example.wintercamp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    height:Dp = 86.dp,
    horizontalPadding: Dp = 0.dp,
    bottomPadding:Dp = 34.dp,
    selfCalled: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = {}
) {
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(start = horizontalPadding, end = horizontalPadding, bottom = bottomPadding),
        value = if (!selfCalled) value else "",
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = label,
                color = Color.Gray,
                modifier = Modifier.offset(x = 5.dp, y = (-3).dp)
            )
        },
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions
    )
}