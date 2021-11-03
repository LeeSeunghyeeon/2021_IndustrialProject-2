package com.example.welt

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.welt.databinding.FragmentContentBinding
import com.example.welt.databinding.FragmentWeekFeaturesBinding


class Week_Features : Fragment() {
    private lateinit var binding: FragmentWeekFeaturesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeekFeaturesBinding.inflate(inflater, container, false)
        binding.OKbtn.setOnClickListener{
            //val show_content = activity?.supportFragmentManager?.beginTransaction()
            //val frameLayout = activity?.supportFragmentManager?.findFragmentById(R.id.framelayout_week)
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().remove(Week_Features()).commit()
                fragmentManager.popBackStack()
            }

        }
        return binding.root
    }


}