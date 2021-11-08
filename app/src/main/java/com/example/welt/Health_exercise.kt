package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthExerciseBinding
import kotlinx.android.synthetic.*

class Health_exercise : DialogFragment() {
    private lateinit var binding: FragmentHealthExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthExerciseBinding.inflate(inflater, container, false)
        binding.BtnExerciseCancel.setOnClickListener{
            dismiss()
        }
        binding.BtnExerciseOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}