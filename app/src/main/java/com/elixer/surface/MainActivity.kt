package com.elixer.surface

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elixer.surface.ui.theme.SurfaceTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt


class MainActivity : ComponentActivity(), SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    val text = MutableStateFlow("Empty Text")
    val angleText = MutableStateFlow("0 Text")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            initialize()
            SurfaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
                    Column {
                        CircleShape()
                        text()
                        angletext()
                    }
                }
            }
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val xAxis = p0?.values?.get(0);
        val yAxis = p0?.values?.get(1);
        Log.e("xAxis", xAxis.toString())
        Log.e("yAxis", yAxis.toString())
        if (xAxis != null && yAxis != null) {
            val angle = Math.atan2(xAxis.toDouble(), yAxis.toDouble()) / (Math.PI / 180);
            angleText.value = angle.roundToInt().toString()
            Log.e("angle", angle.toString())
        }
        text.value = xAxis.toString() + yAxis.toString()
//        if (!isLandscape) {
//            if (angle > 80) {
//                Orientation = 90;
//                isLandscape = true;
//            }
//        } else {
//
//            if (Math.abs(angle) < 10) {
//                Orientation = 0;  //portrait
//                isLandscape = false;
//            }
//        }
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    @Composable
    fun initialize() {
        val context = LocalContext.current
        mSensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager?;
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.e("mAccelerometer", mSensorManager.toString())

    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        Log.e("sensorManager", mSensorManager.toString())
    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(this);

    }

    @Composable
    fun text() {
        val myText by text.collectAsState()
        Text(myText)
    }

    @Composable
    fun angletext() {
        val myText by angleText.collectAsState()
        Text(myText)
    }

}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Composable
fun CircleShape() {
    RoundedMetallicSurface(shape = CircleShape)
}

@Composable
fun RoundedMetallicSurface(shape: Shape) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(shape)
//                .background(Color.Red)
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFFF5F5F5),
                            Color(0xFF5B5B5B),
                            Color(0xFFC5C5C5),
                            Color(0xFFF5F5F5),
                            Color(0xFF5B5B5B),
                            Color(0xFFC5C5C5),
                            Color(0xFFF5F5F5),
                            Color(0xFF5B5B5B),
                            Color(0xFFC5C5C5),
                            Color(0xFFF5F5F5),
                        )
                    )
                )

        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    SurfaceTheme {
        Greeting("Android")
        CircleShape()
    }
}