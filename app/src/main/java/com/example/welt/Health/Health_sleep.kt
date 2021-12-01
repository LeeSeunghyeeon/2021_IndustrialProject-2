package com.example.welt.Health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthSleepBinding

class Health_sleep : DialogFragment() {

    private lateinit var binding: FragmentHealthSleepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    // 취침 시간, 기상 시간 계산해서 수면시간 저장
    // try문 이용해서 처음 입력: insert, 이후에는 update

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthSleepBinding.inflate(inflater, container, false)
        val startTP:TimePicker = binding.tpSleepstart
        val endTP:TimePicker = binding.tpSleepEnd

        binding.BtnSleepOK.setOnClickListener {
            // 취침 시간
            var startHour = startTP.currentHour
            var startMinute = startTP.currentMinute
            // 기상 시간
            var endHour = endTP.currentHour
            var endMinute = endTP.currentMinute

            var sleepTime = 0
            var sleepHour = 0
            var sleepMinute = 0
            // 자정 전 밤에 취침 (22시 or 23시)
            if (startHour > 12) {
                sleepTime = (24*60 - (startHour*60 + startMinute)) + endHour * 60 + endMinute
                sleepHour = sleepTime / 60
                sleepMinute = sleepTime % 60
            }
            // 자정 지나서 12시 넘어서 0시 or 1시 취침
            else {
                sleepTime = (endHour*60 + endMinute) - (startHour*60 + startMinute)
                sleepHour = sleepTime / 60
                sleepMinute = sleepTime % 60
            }

            // 파이어베이스에 총 수면 시간 (ex 7(sleepHour)시간 23(sleepMinute)분 입력

            var message = "오늘의 수면 시간: " + sleepHour.toString() + "시간 " + sleepMinute.toString() + "분"

            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show()

            dismiss()
        }

        binding.BtnSleepCancel.setOnClickListener{
            dismiss()
        }

        return binding.root
    }

}