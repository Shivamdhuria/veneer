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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
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
import kotlin.math.roundToInt

@Composable
fun metallicComponent(
    canvasSize: Dp = 300.dp,
    rotationValue: Float,
    colors: List<Color> = listOf(
        WHITE200,
        WHITE200,
        WHITE400,
        WHITE800,
        WHITE200,
        WHITE400,
        WHITE400,
        WHITE800,
        WHITE200,
    )
) {
    val lastRotation = remember { mutableStateOf(0f) } // this keeps last rotation
    val difference = rotationValue - lastRotation.value
    val time = 900 / (difference.absoluteValue)
    lastRotation.value = rotationValue

    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = time.roundToInt(),
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
                rotate(degrees = angle) {
                    drawCircle(colors)
                }
            },

        ) {
        getTriangle()
    }
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

fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)

fun DrawScope.drawCircle(
    colors: List<Color>
) {
    drawCircle(
        brush = Brush.sweepGradient(
            colors
        ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun customComponentPreview() {
    metallicComponent(canvasSize = 100.dp, rotationValue = 0f)
}