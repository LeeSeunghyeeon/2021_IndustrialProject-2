package com.example.welt.Health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
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

                if (weight > 0) {
                    // 날짜에 맞게 데이터 베이스에 저장
                } else {
                    Toast.makeText(getActivity(), "몸무게를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
                }

                dismiss()

            } catch (e:Exception){
                Toast.makeText(getActivity(), "몸무게를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

        binding.BtnWeightCancel.setOnClickListener{
            dismiss()
        }

        return binding.root
    }

}

