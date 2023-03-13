package com.example.wintercamp.ui.animate

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class BreathingData(
    float: State<Float>,
    color: State<Color>,
    myColor: State<MyColor>/*该参数仅用作示范*/
) {
    val color by color
    val float by float
    val myColor by myColor
}

/**
 * 示例类，看完就可以删了
 */
data class MyColor(
    val r: Int,
    val g: Int,
    val b: Int,
)

/**
 * 呼吸动画使用样例
 */
@Preview
@Composable
fun BreathingSample() {
    val transitionData = breathingTransitionData()
    Column {

        OutlinedButton(onClick = {}, Modifier.scale(transitionData.float)) {
            Text(text = "大大大大大大大大大大按钮")
        }
        Text(text = "color动画", color = transitionData.color)
        val color = Color(transitionData.myColor.r, transitionData.myColor.g, transitionData.myColor.b)
        Text(text = "自定义类的动画", color = color)
    }
}


@Composable
fun breathingTransitionData(): BreathingData {
    val infiniteTransition = rememberInfiniteTransition()
    /*
    使用样例
    val alpha = infiniteTransition.animateFloat(
        initialValue = 0f,//初始值，可以修改
        targetValue = 1f,//结束值，可以修改
        animationSpec = infiniteRepeatable(
            animation = keyframes { //使用关键帧动画
                durationMillis = 5000//持续时间5000
                0.4f at 1000 with EaseInOutSine//数值 at 时间 [with Easing]
                0.8f at 45000 with EaseInOutSine//数值 at 时间 [with Easing]
                1f at 5000 with EaseInOutSine//用sin函数进行插值，即以sin函数的形式从4500ms过渡到5000ms
            },
            repeatMode = RepeatMode.Restart//动画结束会重新开始
        )
    )
     */

    val float = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                //呼吸时长建议2:3，深呼吸在3~5s，此处取2s:3s
                durationMillis = 5000
                0f at 2000 with EaseInOutSine
            },
            repeatMode = RepeatMode.Restart//动画结束会重新开始
        )
    )
    val color = infiniteTransition.animateColor(
        initialValue = Color.Cyan,//初始颜色
        targetValue = Color.Cyan,
        animationSpec = infiniteRepeatable(
                animation = keyframes {
                    //呼吸时长建议2:3，深呼吸在3~5s，此处取2s:3s
                    durationMillis = 5000
                    Color.Yellow at 2000 with EaseInOutSine//中间颜色
                },
                repeatMode = RepeatMode.Restart//动画结束会重新开始
            )
    )
    //自定义类型的动画
    val value = infiniteTransition.animateValue(
        initialValue = MyColor(255, 0, 0),
        targetValue = MyColor(0, 255, 255),
        typeConverter = TwoWayConverter(/*调用这个方法实现TwoWayConverter接口*/
            convertToVector = { color: MyColor ->
                //将自定义类型转化为AnimationVector3D
                //对于不同维度的数据，还有AnimationVector1D,AnimationVector2D,AnimationVector4D
                AnimationVector3D(color.r.toFloat(), color.g.toFloat(), color.b.toFloat())
            },
            convertFromVector = { vector: AnimationVector3D ->
                //将vector转回自定义数据
                MyColor(vector.v1.toInt(), vector.v2.toInt(), vector.v3.toInt())
            }
        ),
        animationSpec = infiniteRepeatable(
            tween(2000)
        )
    )
    return BreathingData(
        float = float,
        color = color,
        myColor = value
    )
}
