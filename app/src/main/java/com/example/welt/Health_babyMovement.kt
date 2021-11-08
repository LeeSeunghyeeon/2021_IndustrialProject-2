package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import com.example.welt.databinding.FragmentContenetVisitHospitalBinding
import com.example.welt.databinding.FragmentHealthBabyMovementBinding


class Health_babyMovement : Fragment() {
    private lateinit var binding: FragmentHealthBabyMovementBinding

=======
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentHealthBabyMovementBinding


class Health_babyMovement : DialogFragment() {
    private lateinit var binding: FragmentHealthBabyMovementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
>>>>>>> 9402a92131393ac3166ea96cefe7bb01bf42d682
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthBabyMovementBinding.inflate(inflater, container, false)
<<<<<<< HEAD
        binding.BtnSave.setOnClickListener {
           // dismiss()
        }
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_health, container, false) }
    }
}
=======
        binding.BtnBabymcancle.setOnClickListener {
            dismiss()
        }
        binding.BtnBabymOK.setOnClickListener {
            dismiss()
        }
        return binding.root

    }

}
>>>>>>> 9402a92131393ac3166ea96cefe7bb01bf42d682
