package com.example.welt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import com.example.welt.databinding.FragmentContentBinding
import com.example.welt.databinding.FragmentHealthBinding
=======
import com.example.welt.databinding.FragmentHealthBinding
import com.example.welt.databinding.FragmentMissionBinding
>>>>>>> 9402a92131393ac3166ea96cefe7bb01bf42d682


class HealthFragment : Fragment() {
    private lateinit var binding: FragmentHealthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD

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
=======
        binding = FragmentHealthBinding.inflate(inflater, container, false)
        setButtonClickEvent()
        return binding.root
    }

    private fun setButtonClickEvent(){
        // 운동
        binding.BtnExercise.setOnClickListener{
            val exerciseDialog = Health_exercise()
            activity?.supportFragmentManager?.let { fragmentManager ->
                exerciseDialog.show(fragmentManager, "Health_exercise_dialog")
            }
        }

        // 식사
        binding.BtnMeal.setOnClickListener{
            val exerciseDialog = Health_meal()
            activity?.supportFragmentManager?.let { fragmentManager ->
                exerciseDialog.show(fragmentManager, "Health_meal_dialog")
            }
        }

        // 수면
        binding.BtnSleep.setOnClickListener{
            val exerciseDialog = Health_sleep()
            activity?.supportFragmentManager?.let { fragmentManager ->
                exerciseDialog.show(fragmentManager, "Health_sleep_dialog")
            }
        }

        // 체중
        binding.BtnWeight.setOnClickListener{
            val exerciseDialog = Health_weight()
            activity?.supportFragmentManager?.let { fragmentManager ->
                exerciseDialog.show(fragmentManager, "Health_weight_dialog")
            }
        }

        // 태동
        binding.BtnBabyMovement.setOnClickListener{
            val exerciseDialog = Health_babyMovement()
            activity?.supportFragmentManager?.let { fragmentManager ->
                exerciseDialog.show(fragmentManager, "Health_babyMovement_dialog")
            }
        }

        // 복약
        binding.BtnMedicineManage.setOnClickListener{
            val exerciseDialog = Health_medicineManage()
            activity?.supportFragmentManager?.let { fragmentManager ->
                exerciseDialog.show(fragmentManager, "Health_medicineManage_dialog")
            }
        }
    }

>>>>>>> 9402a92131393ac3166ea96cefe7bb01bf42d682
}