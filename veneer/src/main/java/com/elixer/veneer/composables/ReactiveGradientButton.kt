package com.elixer.veneer.composables

import androidx.compose.animation.core.Easing
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elixer.veneer.BLUE
import com.elixer.veneer.PINK
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReactiveGradientButton(
    rotationValue: Float,
    colorBegin: Color = PINK,
    colorEnd: Color = BLUE,
    animationDurationInMillis: Int = 300,
    animationEasing: Easing = LinearEasing,
    sensitivityInverseConstant: Float = 90f,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit


) {
    // this keeps last rotation, will use it later for something
    val lastRotation = remember { mutableStateOf(0f) }

    val angle: Float by animateFloatAsState(
        targetValue = rotationValue / sensitivityInverseConstant,
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
            easing = animationEasing
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
                            val componentSize = size / 1.25f
                            drawRoundedRectangle(colorBegin, colorEnd, angle)
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

fun DrawScope.drawRoundedRectangle(
    colorBegin: Color,
    colorEnd: Color,
    angle: Float
) {
    drawRoundRect(
        brush = Brush.horizontalGradient(
            0.0f + angle to colorBegin,
            1.0f + angle to colorEnd,

            )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun modernButtonPreview() {
    ReactiveGradientButton(rotationValue = 0f, onClick = {}) {
        Text(text = "dsjdjkshd")

    }
}