package com.elixer.veneer.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.veneer.*

import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RadialReflectiveButton(
    canvasSize: Dp = 300.dp,
    rotationValue: Float,
    colorList: List<Color> = listOf(
        WHITE200,
        WHITE200,
        WHITE400,
        WHITE800,
        WHITE200,
        WHITE400,
        WHITE400,
        WHITE800,
        WHITE200,
    ),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    colorBegin: Color = PINK,
    colorEnd: Color = BLUE,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
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
    val contentColor by colors.contentColor(enabled)
    Surface(
        modifier = modifier,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
        onClick = onClick,
        enabled = enabled,
        role = Role.Button,
        interactionSource = interactionSource,
        indication = rememberRipple()
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .drawBehind {
                            rotate(degrees = angle) {
                                drawCircle(colorList)
                            }
                        }
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }

//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .size(canvasSize)
//            .drawBehind {
//                val componentSize = size / 1.25f
//                rotate(degrees = angle) {
//                    drawCircle(colors)
//                }
//            },
//
//        ) {
//        getTriangle()
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
        radius = size.maxDimension
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun customComponentPreview() {
    RadialReflectiveButton(canvasSize = 100.dp, rotationValue = 0f, onClick = {}){

    }
}