package com.elixer.surface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elixer.surface.ui.lineTo
import com.elixer.surface.ui.moveTo
import com.elixer.surface.ui.theme.GOLD800
import com.elixer.surface.ui.theme.GREY600
import com.elixer.surface.ui.theme.GREY800
import com.elixer.surface.ui.theme.SurfaceTheme
import com.elixer.veneer.Veneer
import com.elixer.veneer.composables.LinearReflectiveButton
import com.elixer.veneer.composables.RadialReflectiveButton
import com.elixer.veneer.composables.ReactiveGradientButton

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scrollState = rememberScrollState()

            val backgroundImage = painterResource(R.drawable.texture)
            val azimuthAngle by Veneer.rollAngle.collectAsState()
            val pitchAngle by Veneer.rollAngle.collectAsState()
            val rollAngle by Veneer.rollAngle.collectAsState()
            SurfaceTheme() {
                Surface() {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 40.dp)
                                .verticalScroll(scrollState)
                        ) {
                            Text(text = "veneer", fontSize = 90.sp, color = Color.DarkGray)
                            Text(text = "reactive buttons", fontSize = 20.sp, color = Color.Gray)
                            ModernGradientButtons(rollAngle)
                            Spacer(modifier = Modifier.height(20.dp))
                            ReflectiveButtons(rollAngle)
                            Spacer(modifier = Modifier.height(20.dp))
                            LinearReflectiveButton(rollAngle)


//                            modernButton(canvasSize = 200.dp, rotationValue = rollFlo)
//                            modernButton(canvasSize = 200.dp, rotationValue = rollAngle)

                            angletext(azimuthAngle, pitchAngle, rollAngle)
                            RadianText()


                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Veneer.init(this)
    }

    override fun onPause() {
        super.onPause()
        Veneer.stop()
    }

    @Composable
    fun angletext(azimuth: Float, pitchAngle: Float, rollAngle: Float) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Azimuth")
            Text("Pitch")
            Text("Roll")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(azimuth.toString())
            Text(pitchAngle.toString())
            Text(rollAngle.toString())
        }
    }

    @Composable
    fun RadianText() {
//        val azi by azimuthRadian.collectAsState()
//        val pitch by pitchRadian.collectAsState()
//        val roll by rollRadian.collectAsState()
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(azi)
//            Text(pitch)
//            Text(roll)
//        }
    }
}

@Composable
fun ReflectiveButtons(rollAngle: Float) {

    Text(text = "reflective buttons", fontSize = 20.sp, color = Color.Gray)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        RadialReflectiveButton(
            rotationValue = rollAngle, onClick = { println("pressed") },
            shape = RoundedCornerShape(50)
        ) {
            Icon(
                Icons.Outlined.Pause, contentDescription = "content description", tint = GREY600,

                )
        }
        RadialReflectiveButton(
            rotationValue = rollAngle, onClick = { println("pressed") },
            modifier = Modifier.size(70.dp),  //avoid the oval shape
            shape = CircleShape,
        ) {
            Icon(
                Icons.Filled.PlayArrow, contentDescription = "content description", tint = GREY600,
                modifier = Modifier.size(100.dp)
            )
        }
        RadialReflectiveButton(
            rotationValue = rollAngle, onClick = { println("pressed") },
            shape = RoundedCornerShape(50)
        ) {
            Icon(
                Icons.Filled.Stop, contentDescription = "content description", tint = GREY600,

                )
        }
    }

}

@Composable
fun ModernGradientButtons(rollAngle: Float) {

    Text(text = "modern gradient buttons", fontSize = 20.sp, color = Color.Gray)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        ReactiveGradientButton(
            onClick = { }, rotationValue = rollAngle, shape = RoundedCornerShape(10)
        ) {
            Text(
                text = "Modern Button", fontSize = 20.sp,
                modifier = Modifier.padding(10.dp),
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        ReactiveGradientButton(
            rotationValue = rollAngle,
            onClick = { /*TODO*/ },
            modifier = Modifier.size(50.dp), //avoid the oval shape
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),  //avoid the little icon
        ) {
            Icon(Icons.Filled.PlayArrow, contentDescription = "content description", tint = Color.White)
        }
    }
}


@Composable
fun LinearReflectiveButton(rollAngle: Float) {

    Text(text = " linear reflective buttons", fontSize = 20.sp, color = Color.Gray)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        LinearReflectiveButton(
            rotationValue = rollAngle, onClick = { println("pressed") },
            shape = RoundedCornerShape(50),
            modifier = Modifier.width(300.dp)
        ) {
            Icon(
                Icons.Filled.AccountBalance, contentDescription = "content description", tint = GOLD800,

                )
        }
    }

}

@Composable
fun Labels() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Azimuth")
        Text("Pitch")
        Text("Roll")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    SurfaceTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ModernGradientButtons(0f)
            Spacer(modifier = Modifier.height(20.dp))
            ReflectiveButtons(0f)
            Spacer(modifier = Modifier.height(20.dp))
            LinearReflectiveButton(rollAngle = 0f)

        }
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