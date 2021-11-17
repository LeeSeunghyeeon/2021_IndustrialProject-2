package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthExerciseBinding
import kotlinx.android.synthetic.*

class Health_exercise : DialogFragment() {
    private lateinit var binding: FragmentHealthExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthExerciseBinding.inflate(inflater, container, false)

        // 스피너
        val spinner: Spinner = binding.spinnerExercise
        ArrayAdapter.createFromResource(
            requireContext(), R.array.exercise_arr, android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
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

        // 저장 버튼
        binding.BtnExerciseOK.setOnClickListener {
            // 저장 시 실행할 코드
            dismiss()
        }

        return binding.root
    }
}