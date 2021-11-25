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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elixer.surface.ui.metallicComponent
import com.elixer.surface.ui.modernButton
import com.elixer.surface.ui.theme.SurfaceTheme
import com.elixer.surface.ui.theme.WHITE200
import com.elixer.surface.ui.theme.WHITE400
import com.elixer.surface.ui.theme.WHITE800
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import kotlin.math.roundToInt


class MainActivity : ComponentActivity(), SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private lateinit var sensorManager: SensorManager

    val azimuthAngle = MutableStateFlow("")
    val azimuthRadian = MutableStateFlow("")

    val pitchAngle = MutableStateFlow("")
    val pitchRadian = MutableStateFlow("")

    val rollAngle = MutableStateFlow("")
    val rollAngleFloat = MutableStateFlow(600f)
    val rollRadian = MutableStateFlow("")

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private val orientationAnglesCompose = MutableStateFlow("unknown")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val backgroundImage = painterResource(R.drawable.texture)
            val rollFlo by rollAngleFloat.collectAsState()
            initialize()
            SurfaceTheme() {
                // A surface container using the 'background' color from the theme
                Surface() {
                    Box(modifier = Modifier.fillMaxSize()) {
//                        Image(
//                            painter = backgroundImage, contentDescription = "sdsd",
//                            contentScale = ContentScale.FillBounds,
//                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 40.dp)
                        ) {
//                            Spacer(modifier = Modifier.height(20.dp))
//                            metallicComponent(canvasSize = 200.dp,rotationValue = rollFlo/1.2f)
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "veneer",fontSize = 90.sp, color = Color.DarkGray)
                            Text(text = "reactive buttons",fontSize = 20.sp, color = Color.DarkGray)
                            Spacer(modifier = Modifier.height(200.dp))
                            modernButton(canvasSize = 200.dp, rotationValue = rollFlo)
                            Spacer(modifier = Modifier.height(200.dp))
                            Labels()
                            angletext()
                            RadianText()
//                            Text(text = rollFlo.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }
        updateOrientationAngles()
    }

    fun updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )

        // "rotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles)
        azimuthAngle.value = orientationAngles[0].toString()
        pitchAngle.value = orientationAngles[1].toString()
        rollAngle.value = orientationAngles[2].toString()

        azimuthRadian.value = (orientationAngles[0] * (180 / Math.PI)).roundToInt().toString()
        pitchRadian.value = (orientationAngles[1] * (180 / Math.PI)).roundToInt().toString()
        rollRadian.value = (orientationAngles[2] * (180 / Math.PI)).roundToInt().toString()


        orientationAnglesCompose.value = " Azimuth ${orientationAngles[0].roundToInt()}     pitch  ${orientationAngles[1].roundToInt()} roll  ${orientationAngles[2].roundToInt()}"
        rollAngleFloat.value = (orientationAngles[2] * (180 / Math.PI)).toFloat()
        // "orientationAngles" now has up-to-date information.
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    @Composable
    fun initialize() {
        val context = LocalContext.current
        mSensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager?;
//        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        registerListener()
        Log.e("mAccelerometer", mSensorManager.toString())

    }

    override fun onResume() {
        super.onResume()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        registerListener()
        Log.e("sensorManager", mSensorManager.toString())
    }

    private fun registerListener() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
//                SensorManager.SENSOR_DELAY_NORMAL,
                1000000,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager.registerListener(
                this,
                magneticField,
//                SensorManager.SENSOR_DELAY_NORMAL,
                1000000,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(this);
        sensorManager.unregisterListener(this)
    }

    @Composable
    fun angletext() {
        val azi by azimuthAngle.collectAsState()
        val pitch by pitchAngle.collectAsState()
        val roll by rollAngle.collectAsState()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(azi)
            Text(pitch)
            Text(roll)
        }
    }

    @Composable
    fun RadianText() {
        val azi by azimuthRadian.collectAsState()
        val pitch by pitchRadian.collectAsState()
        val roll by rollRadian.collectAsState()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(azi)
            Text(pitch)
            Text(roll)
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
        Labels()
    }
}