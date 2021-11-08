package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthBabyMovementBinding


    class Health_babyMovement : DialogFragment() {
    private lateinit var binding: FragmentHealthBabyMovementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthBabyMovementBinding.inflate(inflater, container, false)

        binding.BtnBabymcancle.setOnClickListener {
            dismiss()
        }
        binding.BtnBabymOK.setOnClickListener {
            dismiss()
        }
        return binding.root

    }

}
