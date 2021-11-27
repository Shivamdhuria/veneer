package com.elixer.surface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elixer.surface.ui.theme.SurfaceTheme
import com.elixer.veneer.Veneer
import com.elixer.veneer.composables.modernBackground
import com.elixer.veneer.modernButton

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val backgroundImage = painterResource(R.drawable.texture)
            val azimuthAngle by Veneer.rollAngle.collectAsState()
            val pitchAngle by Veneer.rollAngle.collectAsState()
            val rollAngle by Veneer.rollAngle.collectAsState()
            SurfaceTheme() {
                Surface() {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 40.dp)
                        ) {
//                            Spacer(modifier = Modifier.height(20.dp))
//                            metallicComponent(canvasSize = 200.dp,rotationValue = rollFlo/1.2f)
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "veneer", fontSize = 90.sp, color = Color.DarkGray)
                            Text(text = "reactive buttons", fontSize = 20.sp, color = Color.DarkGray)
                            Spacer(modifier = Modifier.height(100.dp))
//                            modernButton(canvasSize = 200.dp, rotationValue = rollFlo)
//                            modernButton(canvasSize = 200.dp, rotationValue = rollAngle)
                            modernBackground(
                                onClick = { println("pressed") }, rotationValue = rollAngle, shape = RoundedCornerShape(10)
                            ) {
                                Text(
                                    text = "Modern Button", fontSize = 20.sp,
                                    modifier =  Modifier.padding(10.dp),
                                )
                            }
                            Spacer(modifier = Modifier.height(200.dp))
                            Labels()
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
        Labels()
    }
}