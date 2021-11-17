package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthMedicineManageBinding

class Health_medicineManage : DialogFragment() {
    private lateinit var binding: FragmentHealthMedicineManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthMedicineManageBinding.inflate(inflater, container, false)
        
        binding.addMedicine.setOnClickListener { 
            // 약 추가하기
        }

        // 삭제하기

        binding.BtnBabymcancle.setOnClickListener {
            dismiss()
        }
        binding.BtnBabymOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}