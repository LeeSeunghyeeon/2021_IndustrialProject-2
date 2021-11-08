package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthMealBinding


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
        binding.BtnMealCancel.setOnClickListener {
            dismiss()
        }
        binding.BtnMealOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}

