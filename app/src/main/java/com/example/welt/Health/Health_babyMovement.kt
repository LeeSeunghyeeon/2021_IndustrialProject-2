package com.example.welt.Health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthBabyMovementBinding
import java.lang.Exception


class Health_babyMovement : DialogFragment() {
    private lateinit var binding: FragmentHealthBabyMovementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthBabyMovementBinding.inflate(inflater, container, false)
        val startTP: TimePicker = binding.timePickerStart
        val endTP: TimePicker = binding.timePickerEnd
        // 저장 버튼
        binding.BtnBabymOK.setOnClickListener {
            // 시작 시간 값 가져오기
            var startHour = startTP.currentHour // 24시간 형식 (ex: 오후 3시 -> 15시)
            var startMinute = startTP.currentMinute
            println(startHour)
            println(startMinute)

            // 종료 시간 값 가져오기
            var endHour = endTP.currentHour
            var endMinute = endTP.currentMinute

            // 횟수 가져오기
            try {
                var count = Integer.parseInt(binding.babyMCount.getText().toString())

                // 파이어베이스에 시작시간, 종료시간, 횟수 삽입
                
                
                // 작업 모두 끝나면 다이얼로그 창 닫기
                dismiss()
            } catch(E:Exception) {
                Toast.makeText(getActivity(), "태동 횟수를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            
        }

        // 취소 버튼
        binding.BtnBabymcancle.setOnClickListener {
            dismiss()
        }

        return binding.root

    }

}
