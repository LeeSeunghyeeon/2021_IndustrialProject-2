package com.example.welt.Content

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.welt.databinding.FragmentContentHighRiskPregnancyTestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Content_HighRiskPregnancyTest : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var binding: FragmentContentHighRiskPregnancyTestBinding
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentHighRiskPregnancyTestBinding.inflate(inflater, container, false)
        setInfo()
        calScore()
        binding.btnOK.setOnClickListener{
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().remove(Content_WeekFeatures()).commit()
                fragmentManager.popBackStack()
            }
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun calScore(){
        var score5 = arrayOf(binding.contentHighCb065, binding.contentHighCb055Height)
        var score10 = arrayOf(
            binding.contentHighCb0110Unger19,
            binding.contentHighCb0210Up35,
            binding.contentHighCb0410BMI,
            binding.contentHighCb2510,
            binding.contentHighCb2710,
            binding.contentHighCb2810,
            binding.contentHighCb2910,
            binding.contentHighCb3010,
            binding.contentHighCb3110
        )
        var score20 = arrayOf(
            binding.contentHighCb0320,
            binding.contentHighCb0720,
            binding.contentHighCb0820,
            binding.contentHighCb0920,
            binding.contentHighCb1020,
            binding.contentHighCb1120,
            binding.contentHighCb1220,
            binding.contentHighCb1320,
            binding.contentHighCb1420,
            binding.contentHighCb1520,
            binding.contentHighCb1620,
            binding.contentHighCb1720,
            binding.contentHighCb1820,
            binding.contentHighCb1920,
            binding.contentHighCb2020,
            binding.contentHighCb2120,
            binding.contentHighCb2220,
            binding.contentHighCb2320,
            binding.contentHighCb2420,
            binding.contentHighCb2620,
            binding.contentHighCb3220
        )
        binding.contentHighCheckScoreBtn.setOnClickListener {
            var score = 0
            for (i in 0..score5.size - 1)
                if(score5[i].isChecked)
                    score = score + 5

            for (i in 0..score10.size - 1)
                if(score10[i].isChecked)
                    score = score + 10

            for (i in 0..score20.size - 1)
                if(score20[i].isChecked)
                    score = score + 20
            binding.contentHighShowScore.setText(score.toString())
            val currentDate: LocalDateTime = LocalDateTime.now() //오늘 날짜
            val today = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))

            myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("HighTest")
            myRef.child(today).child("score").setValue(score)
            Toast.makeText(context, "점수(%d)가 저장되었습니다.".format(score), Toast.LENGTH_SHORT).show()
        }
    }

    fun setInfo() {
        myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString())
        if (uid != null) {
            myRef.child("UserInfo").addValueEventListener(object :ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user_name = snapshot.child("user_name").getValue() as String
                    val user_birth = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(snapshot.child("user_birth").getValue().toString())
                    val user_age = calculateAge(user_birth)
                    binding.contentHighUsernameage.setText("%s(만 %d세,".format(user_name,user_age))
                    if(user_age<=19)
                        binding.contentHighCb0110Unger19.isChecked = true
                    if(user_age>=35)
                        binding.contentHighCb0210Up35.isChecked = true
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed")
                }
            })

            myRef.child("Health").addValueEventListener(object : ValueEventListener{
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user_weight = snapshot.child("20211127").child("weight").getValue() as String
                    val user_height = snapshot.child("20211127").child("height").getValue() as String
                    val user_BMI =  10000 * user_weight.toDouble()/(user_height.toDouble()*user_height.toDouble())
                    if(user_height.toDouble()<=150.0)
                        binding.contentHighCb055Height.isChecked = true
                    if(user_BMI>25)
                        binding.contentHighCb0410BMI.isChecked = true
                    binding.contentHighUserinfo2.setText("%scm, %skg), BMI : %.2f".format(user_height,user_weight,user_BMI))}
                override fun onCancelled(error: DatabaseError) {
                    println("Failed")
                }
            })
        }
    }
    fun calculateAge(date: Date?): Int {
        val birthCalendar = Calendar.getInstance()
        birthCalendar.time = date ?: Date()
        val current = Calendar.getInstance()
        val currentYear = current[Calendar.YEAR]
        val currentMonth = current[Calendar.MONTH]
        val currentDay = current[Calendar.DAY_OF_MONTH]
        var age = currentYear - birthCalendar[Calendar.YEAR]
        if (birthCalendar[Calendar.MONTH] * 100 +
            birthCalendar[Calendar.DAY_OF_MONTH] > currentMonth * 100 + currentDay
        ) age--
        return age
    }
}
