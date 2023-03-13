package com.example.questionnaire

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.wintercamp.R
import com.example.wintercamp.questionnaire.component.CustomText

@Composable
fun ProgressIndicator(section: Int = 1, total: Int = 3) {
    val progress = section.toFloat() / total.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CustomText(
            text = stringResource(R.string.progress_indicator_text).format(section, total),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 30.dp, bottom = 10.dp)
        )
        LinearProgressIndicator(
            modifier = Modifier
                .semantics(mergeDescendants = true) {}
                .fillMaxWidth(),
            progress = animatedProgress
        )
    }
}
