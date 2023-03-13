package com.example.questionnaire

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wintercamp.questionnaire.component.CustomText

@Composable
fun Headline(
    type: String = "",
    headline: String = ""
) {
    Column() {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier
                .padding(start = 10.dp, top = 21.dp, bottom = 10.dp)
                .clip(MaterialTheme.shapes.medium)
        ) {
            CustomText(
                text = type,
                modifier = Modifier.padding(all = 4.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        CustomText(
            text = headline,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(start = 12.dp, bottom = 15.dp),
            fontWeight = FontWeight.Normal
        )
    }
}