package com.example.welt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.welt.databinding.FragmentHealthBinding
import com.example.welt.databinding.FragmentMissionBinding


class HealthFragment : Fragment() {
    private lateinit var binding: FragmentHealthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

}