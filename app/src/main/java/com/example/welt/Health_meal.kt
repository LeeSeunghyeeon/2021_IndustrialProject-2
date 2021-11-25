package com.example.welt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthMealBinding
import kotlinx.android.synthetic.main.fragment_health_meal.*


class Health_meal : DialogFragment() {
    private lateinit var binding: FragmentHealthMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthMealBinding.inflate(inflater, container, false)

        // 아침 메뉴 저장
        binding.breakfastOK.setOnClickListener {
            var breakfast = input_breakfast.text.toString()
            if (breakfast.length > 0) {
                // 저장 -> 데이터베이스에 삽입
                Toast.makeText(requireContext(), breakfast, Toast.LENGTH_LONG).show()

            } else {
                // 저장 X

            }
        }
        // 아침 메뉴 삭제
        binding.breakfastDelete.setOnClickListener {
            var breakfast = input_breakfast.text.toString()
            if (breakfast.length == 0) {
                // 삭제 X
            } else {
                // 삭제
            }
        }

        // 점심 메뉴 저장
        binding.launchOK.setOnClickListener {
            var launch = input_breakfast.text.toString()
            if (launch.length == 0) {
                // 저장 X
            } else {
                // 저장
            }
        }
        // 점심 메뉴 삭제
        binding.launchDelete.setOnClickListener {
            var launch = input_breakfast.text.toString()
            if (launch.length == 0) {
                // 삭제 X
            } else {
                // 삭제
            }
        }

        // 저녁 메뉴 저장
        binding.dinnerOK.setOnClickListener {
            var dinner = input_breakfast.text.toString()
            if (dinner.length == 0) {
                // 저장 X
            } else {
                // 저장
            }
        }

        // 저녁 메뉴 삭제
        binding.dinnerDelete.setOnClickListener {
            var dinner = input_breakfast.text.toString()
            if (dinner.length == 0) {
                // 삭제 X
            } else {
                // 삭제
            }
        }

        // 확인
        binding.BtnMealOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}

