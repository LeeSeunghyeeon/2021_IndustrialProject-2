package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.welt.databinding.FragmentContentBinding
import com.example.welt.databinding.FragmentMissionBinding
import androidx.fragment.app.FragmentActivity
import android.app.Activity
import android.content.Intent
import android.net.Uri

class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        setButtonClickEvent()
        return binding.root
    }

    private fun setButtonClickEvent(){
        binding.btnUntilDate.setOnClickListener{
            val show_diary = activity?.supportFragmentManager?.beginTransaction()
            if (show_diary != null) {
                show_diary.replace(R.id.frameLayout_content,DiaryFragment()).addToBackStack(null).commit()
            }
        }
        binding.button5.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=W5Ik2734fpY"))
            startActivity(intent)
        }
        binding.button7.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gimpo.go.kr/health/contents.do?key=2196"))
            startActivity(intent)
        }
        binding.button8.setOnClickListener{
            val show_week_feature = activity?.supportFragmentManager?.beginTransaction()

            if (show_week_feature != null) {
                show_week_feature.replace(R.id.frameLayout_content,Week_Features()).addToBackStack(null).commit()
            }}
    }

    }