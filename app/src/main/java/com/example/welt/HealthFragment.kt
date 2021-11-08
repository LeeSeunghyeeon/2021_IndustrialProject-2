package com.example.welt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.welt.databinding.FragmentContentBinding
import com.example.welt.databinding.FragmentHealthBinding


class HealthFragment : Fragment() {
    private lateinit var binding: FragmentHealthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHealthBinding.inflate(inflater, container, false)
        showDialog()
        return binding.root

        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_health, container, false)
        }

    private fun showDialog(){
        binding.BtnBabyMovement.setOnClickListener{
            val baby_movement_Dialog = Health_babyMovement()
            activity?.supportFragmentManager?.let { fragmentManager ->
               // baby_movement_Dialog.show(fragmentManager, "Health_babyMovement")
            }
        }

        binding.BtnMedicineManage.setOnClickListener{
            val baby_movement_Dialog = Health_medicineManage()
            activity?.supportFragmentManager?.let { fragmentManager ->
                //baby_movement_Dialog.show(fragmentManager, "Health_medicineManage")
            }
        }

    }
}