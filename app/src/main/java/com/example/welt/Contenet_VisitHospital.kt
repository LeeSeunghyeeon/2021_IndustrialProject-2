package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.welt.databinding.FragmentContenetVisitHospitalBinding
import com.example.welt.databinding.FragmentContentBinding
import com.example.welt.databinding.FragmentContentHighRiskPregnancyTestBinding


class Contenet_VisitHospital : DialogFragment(){
    private lateinit var binding: FragmentContenetVisitHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContenetVisitHospitalBinding.inflate(inflater, container, false)
        binding.VisitiHospitalOKbtn.setOnClickListener{
            dismiss()
        }
        return binding.root
    }

}