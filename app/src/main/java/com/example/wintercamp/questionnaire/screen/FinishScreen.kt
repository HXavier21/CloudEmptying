package com.example.wintercamp.questionnaire.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wintercamp.R
import com.example.wintercamp.ui.navigate.RouteName
import com.example.wintercamp.questionnaire.QuizViewModel
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.theme.WinterCampTheme
import kotlin.concurrent.thread

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