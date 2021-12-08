package com.example.welt.Health

import android.app.Activity
import android.app.Activity.SENSOR_SERVICE
import android.app.Service
import android.content.Context
import android.hardware.SensorEventListener
import android.widget.Button
import android.widget.TextView
import android.hardware.SensorManager
import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.welt.R
import android.hardware.SensorEvent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthPedometerBinding



class Health_pedometer : DialogFragment() , SensorEventListener{
    private lateinit var binding: FragmentHealthPedometerBinding

    private var tView: TextView? = null
    private var resetBtn: Button? = null
    private var lastTime: Long = 0
    private var speed = 0f
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var x = 0f
    private var y = 0f
    private var z = 0f
    private var sensorManager: SensorManager? = null
    private var accelerormeterSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthPedometerBinding.inflate(inflater, container, false)

        sensorManager = getActivity()?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerormeterSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        tView = binding.cntView
        resetBtn = binding.resetBtn
        tView!!.text = "" + cnt

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (accelerormeterSensor != null) sensorManager!!.registerListener(
            this , accelerormeterSensor ,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onStop() {
        super.onStop()
        if (sensorManager != null) sensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            val gabOfTime = currentTime - lastTime
            if (gabOfTime > 100) {
                lastTime = currentTime
                x = event.values[SensorManager.DATA_X]
                y = event.values[SensorManager.DATA_Y]
                z = event.values[SensorManager.DATA_Z]
                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000
                if (speed > SHAKE_THRESHOLD) {
                    tView!!.text = "" + ++cnt
                }
                lastX = event.values[DATA_X]
                lastY = event.values[DATA_Y]
                lastZ = event.values[DATA_Z]
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor , accuracy: Int) {}
    fun mOnClick(v: View) {
        when (v.id) {
            R.id.resetBtn -> {
                cnt = 0
                tView!!.text = "" + cnt
            }
        }
    }

    companion object {
        var cnt = 0
        private const val SHAKE_THRESHOLD = 800
        private const val DATA_X = SensorManager.DATA_X
        private const val DATA_Y = SensorManager.DATA_Y
        private const val DATA_Z = SensorManager.DATA_Z
    }
}