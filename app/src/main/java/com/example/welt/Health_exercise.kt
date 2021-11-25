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

        // 스피너
        val spinner: Spinner = binding.spinnerExercise
        val test = mutableListOf("선택")

        ArrayAdapter.createFromResource(
            requireContext(), R.array.exercise_arr, android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // 현재 날짜 받아오기
        var date = LocalDate.now()

        // 저장 버튼
        binding.BtnExerciseOK.setOnClickListener {
            var exercise = spinner.selectedItem.toString()
            var minute = Integer.parseInt(binding.inputMinute.getText().toString())

            println(date)
            // 10분당 요가(23 kcal), 필라테스(30 kcal) 스트레칭(23kcal), 수영 (160kcal), 자전거(33kcal)
            // 저장 시 실행할 코드
            if (!exercise.equals("선택하세요") && minute > 0) {

                // 저장
                println(exercise)
                println(minute)
                dismiss()
            }

        }

        // 삭제 버튼
        binding.BtnExerciseCancel.setOnClickListener{
            // 취소 시 실행할 코드
            dismiss()
        }

        // 수정 버튼
        binding.BtnExerciseModify.setOnClickListener {
            // 수정 시 실행할 코드
            dismiss()
        }


        return binding.root
    }
}