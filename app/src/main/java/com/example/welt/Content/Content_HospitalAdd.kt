package com.example.welt.Content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import com.example.welt.databinding.FragmentContentHospitalAddBinding

class Content_HospitalAdd : DialogFragment() {

    private lateinit var binding: FragmentContentHospitalAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentContentHospitalAddBinding.inflate(inflater, container, false)
        binding.hospitalAddSaveBtn.setOnClickListener{
            dismiss()
        }
        return inflater.inflate(R.layout.fragment_content__hospital_add, container, false)
    }

}