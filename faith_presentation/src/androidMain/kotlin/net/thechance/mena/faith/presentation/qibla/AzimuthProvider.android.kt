package net.thechance.mena.faith.presentation.qibla


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt


class AndroidAzimuthProvider(private val context: Context) : AzimuthProvider, SensorEventListener {

    private val _azimuth = MutableStateFlow(0f)
    override val azimuthFlow = _azimuth.asStateFlow()

    private var sensorManager: SensorManager? = null
    private var accelValues = FloatArray(3)
    private var magnetValues = FloatArray(3)

    private var initialized = false

    fun initialize() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        initialized = true
    }

    override fun startListening() {
        if (!initialized) throw IllegalStateException("AzimuthProvider not initialized — call initialize() first.")

        sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun stopListening() {
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> accelValues = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> magnetValues = event.values.clone()
        }

        val rotationMatrix = FloatArray(9)
        val orientationValues = FloatArray(3)

        if (SensorManager.getRotationMatrix(rotationMatrix, null, accelValues, magnetValues)) {
            SensorManager.getOrientation(rotationMatrix, orientationValues)
            val azimuthInDegrees = Math.toDegrees(orientationValues[0].toDouble()).toFloat()
            val normalized = (azimuthInDegrees + 360) % 360
            _azimuth.value = normalized.roundToInt().toFloat()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}


