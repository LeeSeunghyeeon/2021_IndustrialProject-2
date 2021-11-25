package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.welt.databinding.FragmentContentBinding
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import com.google.firebase.storage.StorageReference

class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid
    var week = 0
    //var storage = FirebaseStorage.getInstance()
    //var storageRef = storage.reference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        val currentDate: LocalDateTime = LocalDateTime.now() //오늘 날짜
        val show_today = currentDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일", Locale("ko", "KR")))
        val cal_today = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))
        var babyName = "아기"
        binding.contentTodaydate.setText("오늘은 $show_today")
        println(uid)
        if (uid != null) {
            myRef.child("User").child(uid).child("UserInfo").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    babyName = snapshot.child("user_baby_name")?.value as String // 태명
                    val baby_birth = snapshot.child("user_babyBirth")?.value //출산예정일

                    val startDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(cal_today.toString())
                    val endDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(baby_birth.toString())
                    val remaining_days = (endDate.time - startDate.time) / (24 * 60 * 60 * 1000)
                    binding.contentRemainingDays.setText("$remaining_days")

                    week = 41 - (remaining_days/7).toInt()
                    binding.contentShowWeek.setText("%d주차".format(week))
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed")
                }
            })
        }
        myRef.child("WeekInfo").child("WeekInfo").addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var babyHeight = snapshot.child("%d주차".format(week)).child("babyHeight")?.value
                var babyWeight = snapshot.child("%d주차".format(week)).child("babyWeight")?.value
                val fruitName = snapshot.child("%d주차".format(week)).child("fruitName")?.value.toString()
                var photoName = snapshot.child("%d주차".format(week)).child("photoName")?.value

                binding.contentWeekSize.setText("%s(이)는\n현재 %s\n크기입니다.".format(babyName,fruitName))
                binding.contentWeekBody.setText("(%s , %s)".format(babyHeight,babyWeight))
                binding.contentWeekPhoto.setImageResource(R.drawable.week10_prune)

                //val file = storageRef.child("WeekPhoto/$photoName").downloadUrl.addOnSuccessListener {  }

            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed")
            }
        })
        setButtonClickEvent()
        return binding.root
    }

    private fun setButtonClickEvent(){
        binding.contentYoutubeBtn.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=$week%EC%A3%BC%EC%B0%A8+%ED%83%9C%EA%B5%90+"))
            startActivity(intent)
        }
        binding.contentDepressionBtn.setOnClickListener{

            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gimpo.go.kr/health/contents.do?key=2196"))
            startActivity(intent)
        }
        binding.contentMoreBtn.setOnClickListener{
            val show_week_feature = activity?.supportFragmentManager?.beginTransaction()

            if (show_week_feature != null) {
                show_week_feature.replace(R.id.frameLayout_content,Content_WeekFeatures()).addToBackStack(null).commit()
            }}
        binding.contentHighBtn.setOnClickListener{
            val show_Content_HighRiskPregnancyTest = activity?.supportFragmentManager?.beginTransaction()

            if (show_Content_HighRiskPregnancyTest != null) {
                show_Content_HighRiskPregnancyTest.replace(R.id.frameLayout_content,Content_HighRiskPregnancyTest()).addToBackStack(null).commit()
            }}

        binding.contentHospitalBtn.setOnClickListener{
            val show_hospital = activity?.supportFragmentManager?.beginTransaction()
            if (show_hospital != null) {
                show_hospital.replace(R.id.frameLayout_content,Content_Hospital()).addToBackStack(null).commit()
            }
            }
    }

}
