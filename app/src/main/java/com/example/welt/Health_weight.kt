package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthSleepBinding
import com.example.welt.databinding.FragmentHealthWeightBinding
import java.lang.Exception

class Health_weight : DialogFragment() {

    private lateinit var binding:FragmentHealthWeightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthWeightBinding.inflate(inflater, container, false)

        // 저장
        binding.BtnWeightOK.setOnClickListener {

            try {
                var weightET = binding.weight.getText().toString()
                var weight:Float = weightET.toFloat()
                // 날짜에 맞게 데이터 베이스에 저장

            } catch (e:Exception){
                println("다시 입력")
            }

            dismiss()
        }

        binding.BtnWeightCancel.setOnClickListener{
            dismiss()
        }

        return binding.root
    }

}

