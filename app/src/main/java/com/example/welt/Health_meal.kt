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
import java.lang.Exception


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
            try {
                var breakfast = input_breakfast.text.toString()
                if (breakfast.length > 0) {
                    // 저장 -> 데이터베이스에 삽입
                    Toast.makeText(requireContext(), breakfast, Toast.LENGTH_LONG).show()

                } else {
                    // 저장 X
                    Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }

            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        // 아침 메뉴 삭제
        binding.breakfastDelete.setOnClickListener {
            try {
                var breakfast = input_breakfast.text.toString()
                if (breakfast.length > 0) {
                    // 삭제
                } else {
                    // 삭제 X
                }
            } catch (E:Exception) {

            }

        }

        // 점심 메뉴 저장
        binding.launchOK.setOnClickListener {
            try {
                var launch = input_launch.text.toString()
                if (launch.length > 0) {
                    // 저장 -> 데이터베이스에 삽입
                    Toast.makeText(requireContext(), launch, Toast.LENGTH_LONG).show()

                } else {
                    // 저장 X
                    Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }

            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        // 점심 메뉴 삭제
        binding.launchDelete.setOnClickListener {

            try {
                var launch = input_breakfast.text.toString()
                if (launch.length > 0) {
                    // 삭제
                } else {
                    // 삭제 X
                }
            } catch (E:Exception) {

            }
        }

        // 저녁 메뉴 저장
        binding.dinnerOK.setOnClickListener {
            try {
                var dinner = input_breakfast.text.toString()
                if (dinner.length > 0) {
                    // 저장
                } else {
                    // 저장 X
                    Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }


        }

        // 저녁 메뉴 삭제
        binding.dinnerDelete.setOnClickListener {

            try {
                var dinner = input_breakfast.text.toString()
                if (dinner.length > 0) {
                    // 삭제
                } else {
                    // 삭제 X
                }
            } catch (E:Exception) {

            }
        }

        // 확인
        binding.BtnMealOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}

