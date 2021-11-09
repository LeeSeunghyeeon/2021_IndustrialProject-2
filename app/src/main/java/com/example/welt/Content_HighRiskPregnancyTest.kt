package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.welt.databinding.FragmentContentHighRiskPregnancyTestBinding
import com.example.welt.databinding.FragmentContentWeekFeaturesBinding


class Content_HighRiskPregnancyTest : Fragment() {
    private lateinit var binding: FragmentContentHighRiskPregnancyTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentHighRiskPregnancyTestBinding.inflate(inflater, container, false)
        binding.btnOK.setOnClickListener{
            //val show_content = activity?.supportFragmentManager?.beginTransaction()
            //val frameLayout = activity?.supportFragmentManager?.findFragmentById(R.id.framelayout_week)
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().remove(Content_WeekFeatures()).commit()
                fragmentManager.popBackStack()
            }

        }
        return binding.root
    }

}