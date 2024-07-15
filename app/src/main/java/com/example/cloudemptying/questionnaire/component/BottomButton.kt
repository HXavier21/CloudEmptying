package com.example.cloudemptying.questionnaire.component


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cloudemptying.R

@Composable
fun BottomButton(
    onNavigateToPrevious: () -> Unit = {},
    onNavigateToNext: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .absolutePadding(bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onNavigateToPrevious,
            modifier = Modifier
                .weight(15f)
                .padding(all = 5.dp)
        ) {
            CustomText(
                text = stringResource(R.string.previous),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(
            onClick = onNavigateToNext,
            modifier = Modifier
                .weight(15f)
                .padding(all = 5.dp)
        ) {
            CustomText(
                text = stringResource(R.string.next),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}