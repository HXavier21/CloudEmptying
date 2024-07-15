package com.example.cloudemptying.questionnaire.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cloudemptying.R
import com.example.cloudemptying.questionnaire.component.CustomText
import com.example.cloudemptying.ui.theme.WinterCampTheme

@Composable
@Preview
fun FinishScreen(
    onNavigateToEmptying:()->Unit = {}
) {
    WinterCampTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CustomText(
                    text = stringResource(R.string.accomplished),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 275.dp)
                )
                CustomText(
                    text = stringResource(R.string.thank_you_for_your_feedback),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onNavigateToEmptying() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 30.dp)
                ) {
                    CustomText(
                        text = stringResource(R.string.back_to_home),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}