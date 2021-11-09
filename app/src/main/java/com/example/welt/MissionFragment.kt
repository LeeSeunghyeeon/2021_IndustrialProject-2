package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.welt.databinding.FragmentMissionBinding


class MissionFragment : Fragment() {
    private lateinit var binding:FragmentMissionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionBinding.inflate(inflater, container, false)
        return binding.root }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showDialog()
    }

    private fun showDialog(){
        binding.sleepDialogBtn.setOnClickListener{
            val sleepDialog = Mission_SleepDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                sleepDialog.show(fragmentManager, "Mission_SleepDialog")
            }
        }

        binding.walkDialogBtn.setOnClickListener{
            val walkDialog = Mission_walkDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                walkDialog.show(fragmentManager, "Mission_walkDialog")
            }
        }

        binding.eatCalDialogBtn.setOnClickListener{
            val eatCalDialog = Mission_eatCalDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                eatCalDialog.show(fragmentManager, "Mission_eatCalDialog")
            }
        }

        binding.burntCalDialogBtn.setOnClickListener{
            val burntCalDialog = Mission_burntCalDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                burntCalDialog.show(fragmentManager, "Mission_burntCalDialog")
            }
        }

        binding.weightDialogBtn.setOnClickListener{
            val weightDialog = Mission_weightDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                weightDialog.show(fragmentManager, "Mission_weightDialog")
            }
        }

        binding.babyMovementDialogBtn.setOnClickListener{
            val movementDialog = Mission_babyMovementDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                movementDialog.show(fragmentManager, "Mission_babyMovementDialog")
            }
        }

    }
}