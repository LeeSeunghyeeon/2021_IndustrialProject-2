package com.example.welt.Mission

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentMissionWalkDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.time.LocalDate

class Mission_walkDialog : DialogFragment(), SensorEventListener {

    private lateinit var binding: FragmentMissionWalkDialogBinding

    private var tView: TextView? = null
    private var left_tView: TextView? = null
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

    val myRef = database.getReference("User")

    // 현재 user 가져오기
    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid // 현재 로그인한 사용자의 파이어베이스 uid
    // 현재 날짜 받아오기
    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionWalkDialogBinding.inflate(inflater , container , false)
        sensorManager = getActivity()?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerormeterSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        tView = binding.cntView
        left_tView = binding.leftSteps
        tView!!.text = "" + cnt
        left_tView!!.text = "" + (5000 - cnt)

        binding.btnOK.setOnClickListener {
            dismiss()
        }

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
                    left_tView!!.text = "" + (5000 - cnt)
                    try{
                        // 걸음 수 저장
                        myRef.child(userID.toString()).child("Walk").child(date.toString()).setValue(cnt)
                        // 걸음 수 칼로리 저장 (30보당 1kcal)
                        myRef.child(userID.toString()).child("Health").child(date.toString()).child("burntCal").setValue(cnt/30)
                    } catch (E:Exception) {

                    }

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
//            R.id.btnOK -> {
//                //cnt = 0
//                dismiss()
//                //tView!!.text = "" + cnt
//            }
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
/*
override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {



    var rootView =
        inflater.inflate(R.layout.fragment_mission_walk_dialog, container, false) as ViewGroup
////////
    //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    btnOK = rootView.findViewById(R.id.btn_OK)
    btnOK.setOnClickListener {
        dismiss()
    }

    return rootView
}

override fun onClick(v: View?) {
    dismiss()
}*/

