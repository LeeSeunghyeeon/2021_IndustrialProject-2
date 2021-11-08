package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthExerciseBinding
import com.example.welt.databinding.FragmentHealthSleepBinding

class Health_sleep : DialogFragment() {

    private lateinit var binding: FragmentHealthSleepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthSleepBinding.inflate(inflater, container, false)
        binding.BtnSleepCancel.setOnClickListener{
            dismiss()
        }
        binding.BtnSleepOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}