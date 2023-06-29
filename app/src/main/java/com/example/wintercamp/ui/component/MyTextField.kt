package com.example.wintercamp.ui.component

import android.text.BoringLayout
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    height: Dp = 86.dp,
    horizontalPadding: Dp = 0.dp,
    bottomPadding: Dp = 34.dp,
    selfCalled: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    content: String = "",
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
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(start = horizontalPadding, end = horizontalPadding, bottom = bottomPadding),
        value = if (!selfCalled) value else "",
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = content,
                color = Color.Gray,
                modifier = Modifier.offset(x = 5.dp, y = (-3).dp)
            )
        },
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLabelTextField(
    height: Dp = 60.dp,
    horizontalPadding: Dp = 0.dp,
    selfCalled: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = {},
    label: @Composable (() -> Unit)? = {},
    enabled:Boolean = true
) {
    val isFocused = remember { mutableStateOf(false) }

    val containerColor by animateColorAsState(
        if (isFocused.value) Color.White
        else Color(243, 243, 243), label = ""
    )

    OutlinedTextField(
        colors = TextFieldDefaults.textFieldColors(
            containerColor = if (value == "") containerColor else Color.White,
            focusedLabelColor = Color(100, 100, 100),
            unfocusedLabelColor = Color(130, 130, 130),
            focusedIndicatorColor = Color(153, 153, 153),
            unfocusedIndicatorColor = if (value == "") Color.Transparent else Color(153, 153, 153),
            textColor = Color.Black
        ),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            }
            .fillMaxWidth()
            .height(height)
            .padding(start = horizontalPadding, end = horizontalPadding),
        value = if (!selfCalled) value else "",
        onValueChange = onValueChange,
        label = label,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        enabled = enabled
    )
}
