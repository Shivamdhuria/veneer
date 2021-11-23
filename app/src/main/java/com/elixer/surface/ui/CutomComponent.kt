package com.elixer.surface.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.surface.ui.theme.WHITE200
import com.elixer.surface.ui.theme.WHITE400
import com.elixer.surface.ui.theme.WHITE800

@Composable
fun customComponent(
    canvasSize: Dp = 300.dp,
    rotationValue: Float
) {

    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 600,
            easing = LinearEasing
        )
    )
    Canvas(modifier = Modifier.size(canvasSize))

    {
        rotate(degrees = angle) {
            drawMetallicButton(WHITE200, WHITE400, WHITE800)
        }
    }

}


fun DrawScope.drawMetallicButton(
    colorLight: Color,
    colorMid: Color,
    ColorDark: Color,
) {
    drawCircle(
        brush = Brush.sweepGradient(
            listOf(
                colorLight,
                colorMid,
                ColorDark,
                colorLight,
                colorMid,
                ColorDark,
                colorLight,
            )
        ),
    )

}

//fun DrawScope.foregroundImage(
//    sweepAngle: Float,
//    componentSize: Size,
//    indicatorColor: Color,
//    indicatorStrokeWidth: Float,
////    indicatorStokeCap: StrokeCap
//) {
//    drawArc(
//        size = componentSize,
//        color = indicatorColor,
//        startAngle = 150f,
//        sweepAngle = sweepAngle,
//        useCenter = false,
//        style = Stroke(
//            width = indicatorStrokeWidth,
//            cap = StrokeCap.Round
//        ),
//        topLeft = Offset(
//            x = (size.width - componentSize.width) / 2f,
//            y = (size.height - componentSize.height) / 2f
//        )
//    )
//}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun customComponentPreview() {
    customComponent(canvasSize = 100.dp,rotationValue = 0f)
}