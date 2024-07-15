package com.example.cloudemptying.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.cloudemptying.LocalImageLoader
import com.example.cloudemptying.R
import com.example.cloudemptying.questionnaire.component.CustomText
import com.example.cloudemptying.ui.theme.WinterCampTheme

@Preview
@Composable
fun SelfEmptyingItem(
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    text: String = "Go up",
    name: String = "Huster",
    image: Int = R.drawable.self_emptying
) {
    val context = LocalContext.current
    val bubble = ContextCompat.getDrawable(
        context,
        R.drawable.bubble
    )

    WinterCampTheme {
        Column(modifier = modifier) {
            Column(
                modifier = Modifier.height(48.dp)
            ) {
                if (visible) {
                    Column(modifier = Modifier.drawBehind {
                        bubble?.updateBounds(5, 0, size.width.toInt(), size.height.toInt())
                        bubble?.draw(drawContext.canvas.nativeCanvas)
                    }) {
                        CustomText(
                            text = text,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(top = 8.dp, bottom = 20.dp)
                        )
                    }

                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context).data(data = image)
                            .apply(block = {
                                size(Size.ORIGINAL)
                            }).build(), imageLoader = LocalImageLoader.current
                    ),
                    contentDescription = null
                )
                CustomText(
                    text = name,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.offset(y = -12.dp)
                )
            }
        }
    }
}