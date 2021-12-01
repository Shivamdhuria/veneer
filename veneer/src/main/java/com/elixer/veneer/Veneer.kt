package com.elixer.veneer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_UI
import kotlinx.coroutines.flow.MutableStateFlow

object Veneer : SensorEventListener {

    const val SAMPLING_PERIOD_UI = SENSOR_DELAY_UI

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    private var sensorManager: SensorManager? = null

    val rollAngle = MutableStateFlow(0f)
    val pitchAngle = MutableStateFlow(0f)
    val azimuthAngle = MutableStateFlow(0f)

    fun init(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        registerListeners()
    }

    fun stop() {
        unregisterListener()
        sensorManager = null
    }

    private fun registerListeners() {
        sensorManager?.let { sensorManager ->
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
                sensorManager.registerListener(
                    this,
                    accelerometer,

                    SENSOR_DELAY_UI
                )
            }
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
                sensorManager.registerListener(
                    this,
                    magneticField,
                    SAMPLING_PERIOD_UI,
                    SENSOR_DELAY_UI
                )
            }
        }
    }

    private fun unregisterListener() {
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }
        updateOrientationAngles()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )

        // "rotationMatrix" now has up-to-date information.
        SensorManager.getOrientation(rotationMatrix, orientationAngles)
        azimuthAngle.value = (orientationAngles[0] * (180 / Math.PI)).toFloat()
        pitchAngle.value = (orientationAngles[1] * (180 / Math.PI)).toFloat()
        rollAngle.value = (orientationAngles[2] * (180 / Math.PI)).toFloat()
    }
}