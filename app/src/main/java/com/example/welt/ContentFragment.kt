package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.welt.databinding.FragmentContentBinding
import android.content.Intent
import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        setButtonClickEvent()
        return binding.root
    }

    private fun setButtonClickEvent(){
        binding.button5.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=7%EC%A3%BC%EC%B0%A8+%ED%83%9C%EA%B5%90+"))
            startActivity(intent)
        }
        binding.button7.setOnClickListener{
//            myRef.addValueEventListener(object :ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val value = snapshot?.value
//                    binding.button7.setText("$value")
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    println("Failed")
//                }
//            })
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gimpo.go.kr/health/contents.do?key=2196"))
            startActivity(intent)
        }
        binding.button8.setOnClickListener{
            val show_week_feature = activity?.supportFragmentManager?.beginTransaction()

            if (show_week_feature != null) {
                show_week_feature.replace(R.id.frameLayout_content,Content_WeekFeatures()).addToBackStack(null).commit()
            }}
        binding.button4.setOnClickListener{
            val show_Content_HighRiskPregnancyTest = activity?.supportFragmentManager?.beginTransaction()

            if (show_Content_HighRiskPregnancyTest != null) {
                show_Content_HighRiskPregnancyTest.replace(R.id.frameLayout_content,Content_HighRiskPregnancyTest()).addToBackStack(null).commit()
            }}

        binding.button6.setOnClickListener{
            val show_hospital = activity?.supportFragmentManager?.beginTransaction()
            if (show_hospital != null) {
                show_hospital.replace(R.id.frameLayout_content,Content_Hospital()).addToBackStack(null).commit()
            }
            }
    }

}