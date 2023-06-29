package com.example.wintercamp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wintercamp.R
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.navigate.RouteName
import com.example.wintercamp.ui.theme.WinterCampTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun SpecialScreen(
    screen: String = "",
    onNavigateToRegister: () -> Unit = {},
    onNavigateToEmptying: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        coroutineScope.launch {
            delay(3000)
            when (screen) {
                RouteName.Beginning_Screen -> onNavigateToRegister()
                RouteName.Hidden_Screen -> onNavigateToEmptying()
                else -> {}
            }
        }

        onDispose {
        }
    }
    WinterCampTheme {
        Surface(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.special_screen_background),
                contentDescription = null,
                modifier = Modifier.scale(1.05f)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.weight(1.2f),
                    contentAlignment = Alignment.Center
                ) {
                    CustomText(
                        text = when (screen) {
                            RouteName.Beginning_Screen -> stringResource(R.string.emptying_a_cloud)
                            RouteName.Hidden_Screen -> stringResource(R.string.accumulate_cyber_merits_meet_with_miku)
                            else -> ""
                        },
                        style = when (screen) {
                            RouteName.Beginning_Screen -> MaterialTheme.typography.displayMedium
                            RouteName.Hidden_Screen -> MaterialTheme.typography.displaySmall
                            else -> MaterialTheme.typography.displayMedium
                        },
                        color = MaterialTheme.colorScheme.surface
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}