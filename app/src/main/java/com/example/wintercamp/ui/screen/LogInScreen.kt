package com.example.wintercamp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wintercamp.questionnaire.component.CustomText

@Preview
@Composable
fun LogInScreen() {
    Surface(
        Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column {
            Spacer(modifier = Modifier.height(100.dp))
            CustomText(
                text = "Sign in",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)
            )
        }
    }
}