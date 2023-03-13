package com.example.wintercamp.questionnaire.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wintercamp.ui.theme.WinterCampTheme
import com.example.wintercamp.ui.theme.imagelist
import kotlin.String

@Composable
fun SingleChoiceOptionItem(
    modifier: Modifier = Modifier,
    text: String = "Description",
    selected: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium,
    index: Int = 0,
    onClick: () -> Unit = {}
) {
    val animatedDp by animateDpAsState(targetValue = if (selected) 5.dp else 0.dp, tween(100))
    val animatedOutlineColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else
            MaterialTheme.colorScheme.secondaryContainer, tween(100)
    )
    Surface(shape = shape,
        tonalElevation = animatedDp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.5f.dp,
                    color = animatedOutlineColor,
                    shape = shape
                )
                .clickable { onClick() }
                .padding(all = 6.dp)
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.large),
                painter = painterResource(id = imagelist[index % 4]),
                contentDescription = null
            )
            CustomText(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal
            )
            RadioButton(selected = selected,
                onClick = { onClick() },
                modifier = Modifier
                    .padding(end = 24.dp)
                    .semantics { contentDescription = "Localized Description" })
        }
    }
}

@Composable
@Preview
fun PreviewSingleChoice() {
    var selected by remember { mutableStateOf(-1) }
    WinterCampTheme {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for (i in 0..4) {
                item {
                    SingleChoiceOptionItem(selected = selected == i, onClick = { selected = i })
                }
            }
        }

    }
}
