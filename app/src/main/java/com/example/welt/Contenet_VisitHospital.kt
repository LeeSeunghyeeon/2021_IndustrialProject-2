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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Contenet_VisitHospital : DialogFragment(){
    private lateinit var binding: FragmentContenetVisitHospitalBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Contenet_VisitHospital().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}