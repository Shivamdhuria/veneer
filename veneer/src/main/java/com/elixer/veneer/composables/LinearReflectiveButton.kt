package com.elixer.veneer.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.veneer.*

import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LinearReflectiveButton(
    canvasSize: Dp = 300.dp,
    rotationValue: Float,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    colorBegin: Color = GOLD400,
    colorMid: Color = GOLD300,
    colorEnd: Color = GOLD200,
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
    val time = 1000 / (difference.absoluteValue)
    lastRotation.value = rotationValue

    //converting angle to a float value and reducing sensitivity
    val angle: Float by animateFloatAsState(
        targetValue = rotationValue / 120f,
        animationSpec = tween(
            durationMillis = 200,
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
                            drawLinearReflective(colorBegin, colorMid, colorEnd, angle)

                        }
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }

}

fun DrawScope.drawLinearReflective(
    colorBegin: Color,
    colorMid: Color,
    colorEnd: Color,
    angle: Float
) {
    drawRoundRect(
        brush = Brush.horizontalGradient(
//            0.0f to PINK,
            0.0f + angle to colorBegin,
            0.3f + angle to colorMid,
            0.5f + angle to colorEnd,
            0.7f + angle to colorEnd,
            1.0f + angle to colorBegin,

            ),

        )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun linearReflectiveButtonPreview() {
    LinearReflectiveButton(canvasSize = 100.dp, rotationValue = 0f, onClick = {}) {

    }
}