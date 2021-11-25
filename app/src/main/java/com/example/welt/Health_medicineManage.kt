package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
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

        val medicineName = binding.medicineName.getText().toString() // 약 이름
        val spinner2: Spinner = binding.spinnerMedicine2 // 횟수
        val spinner3: Spinner = binding.spinnerMedicine3 // 식전/식후

        ArrayAdapter.createFromResource(
            requireContext(), R.array.countOfMedicine, android.R.layout.simple_spinner_item
        ).also{ adapter2 ->
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter2
        }

        ArrayAdapter.createFromResource(
            requireContext(), R.array.before_after, android.R.layout.simple_spinner_item
        ).also{ adapter3 ->
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner3.adapter = adapter3
        }

        
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