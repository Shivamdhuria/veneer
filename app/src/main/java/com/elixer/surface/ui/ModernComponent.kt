package com.elixer.surface.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.surface.ui.theme.BLUE
import com.elixer.surface.ui.theme.PINK
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun modernButton(
    canvasSize: Dp = 300.dp,
    rotationValue: Float,
    colors: List<Color> = listOf(
        BLUE, PINK
    )
) {
    val lastRotation = remember { mutableStateOf(0f) } // this keeps last rotation
    val difference = rotationValue - lastRotation.value
    val time = 900 / (difference.absoluteValue)
    lastRotation.value = rotationValue

    val angle: Float by animateFloatAsState(
        targetValue = rotationValue / 90f,
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearEasing
        )
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f

                drawRoundedRectangle(colors, angle)

            },

        ) {
//        getTriangle()
        Text(angle.toString())
    }
}

//@Composable
//private fun getTriangle() {
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .aspectRatio(1f)
//            .rotate(0f)
//    ) {
//        val canvasWidth = size.width / 1.9f
//        val canvasHeight = size.height / 2f
//        val rect = Rect(center = Offset(canvasWidth, canvasHeight), canvasWidth / 3f)
//        val trianglePath = Path().apply {
//            moveTo(rect.centerRight)
//            lineTo(rect.bottomLeft)
//            lineTo(rect.topLeft)
//            // note that two more point repeats needed to round all corners
//            lineTo(rect.centerRight)
//            lineTo(rect.bottomLeft)
//        }
//
//        drawIntoCanvas { canvas ->
//            canvas.drawOutline(
//                outline = Outline.Generic(trianglePath),
//                paint = Paint().apply {
//
//                    color = GREY800
//                    pathEffect = PathEffect.cornerPathEffect(rect.maxDimension / 12)
//                }
//            )
//        }
//    }
//}

//fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
//fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)
//
fun DrawScope.drawRoundedRectangle(
    colors: List<Color>,
    angle: Float
) {
    drawRoundRect(
//        brush = Brush.horizontalGradient(
//            colors
//        ),
        brush = Brush.horizontalGradient(
            0.0f to Color.Red,
            1.0f to Color.Blue,
        ),
//        size = Size(
//            width = 300.dp.toPx(),
//            height = 150.dp.toPx()
//        ),
//        topLeft = Offset(
//            x = 60.dp.toPx(),
//            y = 60.dp.toPx()
//        ),
        cornerRadius = CornerRadius(
            x = 20.dp.toPx(),
            y = 20.dp.toPx()
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun modernButtonPreview() {
    modernButton(canvasSize = 100.dp, rotationValue = 0f)
}