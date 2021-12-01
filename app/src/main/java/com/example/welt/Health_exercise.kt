package com.example.welt

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthExerciseBinding
import kotlinx.android.synthetic.*
import java.time.LocalDate

class Health_exercise : DialogFragment() {
    private lateinit var binding: FragmentHealthExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthExerciseBinding.inflate(inflater, container, false)
        // 현재 날짜 받아오기
        var date = LocalDate.now()

        // 스피너
        val spinner: Spinner = binding.spinnerExercise
        val test = mutableListOf("선택")

        ArrayAdapter.createFromResource(
            requireContext(), R.array.exercise_arr, android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // 저장 버튼
        binding.BtnExerciseOK.setOnClickListener {


            // 1분당 요가(2.3 kcal), 필라테스(3.0 kcal) 스트레칭(2.3kcal), 수영 (16.0kcal), 자전거(3.3kcal)
            // 저장 시 실행할 코드

            try {
                // 운동 종목 가져오기
                var exercise = spinner.selectedItem.toString()
                // 운동 시간 가져오기
                var minute = Integer.parseInt(binding.exerciseInputMinute.getText().toString())

                if ((!exercise.equals("선택하세요")) && minute > 0) {
                    // 파이어 베이스 운동 종목, 운동 시간, 칼로리 저장
                    println(exercise)
                    println(minute)
                    dismiss()
                } else {
                    Toast.makeText(getActivity(), "운동 종목과 운동 시간을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()
                }

            } catch (e:Exception) {
                Toast.makeText(getActivity(), "운동 시간을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 삭제 버튼
        binding.BtnExerciseDelete.setOnClickListener{
            // 취소 시 실행할 코드
            dismiss()
        }

        return binding.root
    }
}