package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.welt.databinding.FragmentContenetVisitHospitalBinding
import com.example.welt.databinding.FragmentHealthBabyMovementBinding


class Health_babyMovement : Fragment() {
    private lateinit var binding: FragmentHealthBabyMovementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthBabyMovementBinding.inflate(inflater, container, false)
        binding.BtnSave.setOnClickListener {
           // dismiss()
        }
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_health, container, false) }
    }
}
