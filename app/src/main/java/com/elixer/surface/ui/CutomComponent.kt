package com.elixer.surface.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.surface.ui.theme.GREY800
import com.elixer.surface.ui.theme.WHITE200
import com.elixer.surface.ui.theme.WHITE400
import com.elixer.surface.ui.theme.WHITE800
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt

@Composable
fun customComponent(
    canvasSize: Dp = 300.dp,
    rotationValue: Float
) {
    val lastRotation = remember { mutableStateOf(0f) } // this keeps last rotation
    val difference = rotationValue - lastRotation.value
    val time = 900/(difference.absoluteValue)
    lastRotation.value = rotationValue

    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = time.roundToInt(),
            easing = LinearEasing
        )
    )
//    Canvas(modifier = Modifier.size(canvasSize))

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                rotate(degrees = angle) {
                    drawMetallicButton(WHITE200, WHITE400, WHITE800)
                }
//                foregroundIndicator(
//                    sweepAngle = 240f,
//                    componentSize = componentSize,
//                    indicatorColor = GOLD400,
//                    indicatorStrokeWidth = 40f,
//                )
            },

        ) {
//        Row(Modifier.background(Color.Black), horizontalArrangement = Arrangement.Center) {
        getTriangle()
//        }

    }

//    {
//        rotate(degrees = angle) {
//            drawMetallicButton(WHITE200, WHITE400, WHITE800)
//        }
//    }

}

@Composable
private fun getTriangle() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .rotate(0f)
    ) {
        val canvasWidth = size.width / 1.9f
        val canvasHeight = size.height / 2f
        val rect = Rect(center = Offset(canvasWidth, canvasHeight), canvasWidth / 3f)
//        val rect = Rect(canvasWidth/4f,canvasHeight/4f, size / 2f)
        val trianglePath = Path().apply {
            moveTo(rect.centerRight)
            lineTo(rect.bottomLeft)
            lineTo(rect.topLeft)
            // note that two more point repeats needed to round all corners
            lineTo(rect.centerRight)
            lineTo(rect.bottomLeft)
        }

        drawIntoCanvas { canvas ->
            canvas.drawOutline(
                outline = Outline.Generic(trianglePath),
                paint = Paint().apply {

                    color = GREY800
                    pathEffect = PathEffect.cornerPathEffect(rect.maxDimension / 12)
                }
            )
        }
    }
}

//Column(
//modifier = Modifier
//.size(canvasSize)
//.drawBehind {
//    val componentSize = size / 1.25f
//    backgroundIndicator(
//        componentSize = componentSize,
//        indicatorColor = backgroundIndicatorColor,
//        indicatorStrokeWidth = backgroundIndicatorStrokeWidth,
////                    indicatorStokeCap = indicatorStrokeCap
//    )
//    foregroundIndicator(
//        sweepAngle = sweepAngle,
//        componentSize = componentSize,
//        indicatorColor = foregroundIndicatorColor,
//        indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
////                    indicatorStokeCap = indicatorStrokeCap
//    )
//},
//verticalArrangement = Arrangement.Center,
//horizontalAlignment = Alignment.CenterHorizontally
//) {
//    EmbeddedElements(
//        bigText = receivedValue,
//        bigTextFontSize = bigTextFontSize,
//        bigTextColor = animatedBigTextColor,
//        bigTextSuffix = bigTextSuffix,
//        smallText = smallText,
//        smallTextColor = smallTextColor,
//        smallTextFontSize = smallTextFontSize
//    )
//}
//}
fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)

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

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
//    indicatorStokeCap: StrokeCap
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun customComponentPreview() {
    customComponent(canvasSize = 100.dp, rotationValue = 0f)
}