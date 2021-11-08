package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthSleepBinding
import com.example.welt.databinding.FragmentHealthWeightBinding

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
        binding.BtnWeightCancel.setOnClickListener{
            dismiss()
        }
        binding.BtnWeightOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}