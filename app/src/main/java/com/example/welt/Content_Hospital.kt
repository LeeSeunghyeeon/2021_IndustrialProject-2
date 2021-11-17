package com.example.welt

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.welt.databinding.FragmentContentHospitalBinding
import com.example.welt.databinding.FragmentContentWeekFeaturesBinding

class Content_Hospital : Fragment() {
    private lateinit var binding: FragmentContentHospitalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentHospitalBinding.inflate(inflater, container, false)
        binding.HospitalOKbtn.setOnClickListener{
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().remove(Content_Hospital()).commit()
                fragmentManager.popBackStack()
            }
        }
        binding.hospitalAddBtn.setOnClickListener{
            val addHospitalScheduleDialog = Content_HospitalAdd()
            activity?.supportFragmentManager?.let { fragmentManager ->
                addHospitalScheduleDialog.show(fragmentManager, "Content_addHospitalDialog")
            }
        }
        return binding.root
    }

}