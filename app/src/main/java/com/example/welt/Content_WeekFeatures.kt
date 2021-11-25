package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.welt.databinding.FragmentContentWeekFeaturesBinding

class Content_WeekFeatures : Fragment() {
    private lateinit var binding: FragmentContentWeekFeaturesBinding
    private var url = "https://www.maeili.com/cms/contents/contentsView.do?idx=6859&categoryCd1=1&categoryCd2=2&categoryCd3=0&reCome=1&gubn=1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentWeekFeaturesBinding.inflate(inflater, container, false)
        binding.OKbtn.setOnClickListener{
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().remove(Content_WeekFeatures()).commit()
                fragmentManager.popBackStack()
            }

        }
        return binding.root
    }


}