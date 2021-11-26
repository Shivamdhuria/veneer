//package com.elixer.surface.ui
//
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.CornerRadius
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color.Companion.Blue
//import androidx.compose.ui.graphics.Color.Companion.Red
//import androidx.compose.ui.graphics.Color.Companion.White
//import androidx.compose.ui.graphics.TileMode
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.semantics.SemanticsProperties.Text
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType.Companion.Text
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import com.elixer.surface.ui.theme.BLUE
//import com.elixer.surface.ui.theme.PINK
//import kotlin.math.absoluteValue
//import kotlin.math.roundToInt
//
//@Composable
//fun modernButton(
//    canvasSize: Dp = 300.dp,
//    rotationValue: Float,
//    colorBegin: Color = PINK,
//    colorEnd: Color = BLUE,
//) {
//    val lastRotation = remember { mutableStateOf(0f) } // this keeps last rotation
//    val difference = rotationValue - lastRotation.value
//    val time = 1000 / (difference.absoluteValue)
//    lastRotation.value = rotationValue
//
//    //converting angle to a float value and reducing sensitivity
//    val angle: Float by animateFloatAsState(
//        targetValue = rotationValue / 120f,
//        animationSpec = tween(
//            durationMillis = 200,
//            easing = LinearEasing
//        )
//    )
//
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .size(height = canvasSize / 3, width = canvasSize)
//            .drawBehind {
//                val componentSize = size / 1.25f
//                drawRoundedRectangle(colorBegin,colorEnd, angle)
//
//            },
//
//        ) {
//        Text("Modern Button", fontWeight = FontWeight.Bold, color = White)
////        Text(angle.toString())
//    }
//}
//
//fun DrawScope.drawRoundedRectangle(
//    colorBegin: Color,
//    colorEnd:Color,
//    angle: Float
//) {
//    drawRoundRect(
////        brush = Brush.horizontalGradient(
////            colors
////        ),
//        brush = Brush.horizontalGradient(
////            0.0f to PINK,
//            0.0f + angle to colorBegin,
//            1.0f + angle to colorEnd,
//
//        ),
////        size = Size(
////            width = 300.dp.toPx(),
////            height = 150.dp.toPx()
////        ),
////        topLeft = Offset(
////            x = 60.dp.toPx(),
////            y = 60.dp.toPx()
////        ),
//        cornerRadius = CornerRadius(
//            x = 10.dp.toPx(),
//            y = 10.dp.toPx()
//        )
//    )
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun modernButtonPreview() {
//    modernButton(canvasSize = 100.dp, rotationValue = 0f)
//}