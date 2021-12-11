package com.example.welt.Content

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.welt.Sign.myRef
import com.example.welt.databinding.FragmentContentHighriskviewBinding
import com.example.welt.databinding.FragmentContentHospitalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Content_HighRiskView : DialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentContentHighriskviewBinding
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
        binding = FragmentContentHighriskviewBinding.inflate(inflater, container, false)

        init()
        binding.xBtn.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    fun init() {
        if (uid != null) {
            myRef = FirebaseDatabase.getInstance().getReference("User")
                .child(com.example.welt.Content.uid.toString())
            myRef.addValueEventListener(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentDate: LocalDateTime = LocalDateTime.now() //오늘 날짜
                    val today = currentDate.format(
                        DateTimeFormatter.ofPattern(
                            "yyyyMMdd",
                            Locale("ko", "KR")
                        )
                    )
                    var date = "%s-%s-%s".format(
                        today.substring(0, 4),
                        today.substring(4, 6),
                        today.substring(6, 8)
                    )
                    if (snapshot.child("HighTest").child("score").value != null
                        && snapshot.child("Health").child("$date").child("meal")
                            .child("breakfastCal").value != null
                        && snapshot.child("Health").child("$date").child("meal")
                            .child("launchCal").value != null
                        && snapshot.child("Health").child("$date").child("meal")
                            .child("dinnerCal").value != null
                        && snapshot.child("Health").child("$date").child("burntCal").value != null
                    ) {

                        var totalScore = 0
                        var eatCal = snapshot.child("Health").child("$date").child("meal")
                            .child("breakfastCal").value.toString()
                            .toInt() + snapshot.child("Health").child("$date").child("meal")
                            .child("launchCal").value.toString().toInt() + snapshot.child("Health")
                            .child("$date").child("meal").child("dinnerCal").value.toString()
                            .toInt()
                        var personal_burntCal = snapshot.child("Health").child("$date")
                            .child("burntCal").value.toString().toDouble()
                        var highTestScore =
                            snapshot.child("HighTest").child("score").value.toString().toInt()
                        var height =
                            snapshot.child("UserInfo").child("height").value.toString().toDouble()
                        val splitarr = snapshot.child("Weight").value.toString().split(", ")
                        var weight =
                            splitarr[splitarr.size - 1].split("=")[1].split("}")[0].toDouble()

                        var week = snapshot.child("UserInfo").child("week").value.toString().toInt()
                        val user_age = calculateAge(
                            SimpleDateFormat(
                                "yyyyMMdd",
                                Locale("ko", "KR")
                            ).parse(
                                snapshot.child("UserInfo").child("user_birth").getValue().toString()
                            )
                        )
                        var BMR = 655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * user_age)
                        var recommendedCal = 0
                        if (week >= 1 && week <= 12) {
                            recommendedCal = 2100
                            BMR += 300
                            binding.tv111.setBackgroundColor(Color.parseColor("#FFE8F0"))
                            binding.tv211.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        } else if (week >= 13 && week <= 28) {
                            recommendedCal = 2340
                            BMR += 400
                            binding.tv112.setBackgroundColor(Color.parseColor("#FFE8F0"))
                            binding.tv212.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        } else {
                            recommendedCal = 2451
                            BMR += 500
                            binding.tv113.setBackgroundColor(Color.parseColor("#FFE8F0"))
                            binding.tv213.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        }
                        var burntCal = eatCal - BMR - personal_burntCal

                        if (eatCal <= (recommendedCal + recommendedCal * 0.1) && eatCal >= (recommendedCal - recommendedCal * 0.1)){
                            totalScore += 1
                            binding.tv121.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        }
                        else if (eatCal < (recommendedCal + recommendedCal * 0.15) && eatCal > (recommendedCal + recommendedCal * 0.1)){
                            totalScore += 3
                            binding.tv122.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        }
                        else if (eatCal > (recommendedCal - recommendedCal * 0.15) && eatCal < (recommendedCal - recommendedCal * 0.1)){
                            totalScore += 3
                            binding.tv122.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        }
                        else if (eatCal < (recommendedCal + recommendedCal * 0.2) && eatCal > (recommendedCal + recommendedCal * 0.15)){
                            totalScore += 5
                            binding.tv123.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        }
                        else if (eatCal > (recommendedCal - recommendedCal * 0.2) && eatCal < (recommendedCal - recommendedCal * 0.15)){
                            totalScore += 5
                            binding.tv123.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        }
                        else{
                            totalScore += 7
                            binding.tv124.setBackgroundColor(Color.parseColor("#FFE8F0"))
                        }


                        if (burntCal >= -200 && burntCal <= 200){
                            totalScore += 1
                            binding.tv221.setBackgroundColor(Color.parseColor("#FFE8F0"))}
                        else if (burntCal <= 200 && burntCal > 400){
                            totalScore += 3
                            binding.tv222.setBackgroundColor(Color.parseColor("#FFE8F0"))}
                        else if (burntCal >= -400 && burntCal < -200){
                            totalScore += 3
                            binding.tv222.setBackgroundColor(Color.parseColor("#FFE8F0"))}
                        else if (burntCal <= 400 && burntCal > 600){
                            totalScore += 5
                            binding.tv223.setBackgroundColor(Color.parseColor("#FFE8F0"))}
                        else if (burntCal >= 600 && burntCal < -400){
                            totalScore += 5
                            binding.tv223.setBackgroundColor(Color.parseColor("#FFE8F0"))}
                        else{
                            totalScore += 7
                            binding.tv224.setBackgroundColor(Color.parseColor("#FFE8F0"))}

                        if (highTestScore >= 0 && highTestScore < 40){
                            totalScore += 1
                            binding.tv311.setBackgroundColor(Color.parseColor("#FFE8F0"))}
                        else if (highTestScore >= 40 && highTestScore < 60){
                            totalScore += 7
                            binding.tv312.setBackgroundColor(Color.parseColor("#FFE8F0"))}
                        else if (highTestScore >= 60 && highTestScore < 80){
                            totalScore += 10
                            binding.tv313.setBackgroundColor(Color.parseColor("#FFE8F0"))}
                        else if (highTestScore >= 80){
                            totalScore += 20
                            binding.tv314.setBackgroundColor(Color.parseColor("#FFE8F0"))}

                        if (totalScore <= 6){
                            binding.tv411.setBackgroundColor(Color.parseColor("#FFE8F0"))
                            binding.tv5.setText("\n")}
                        else if (totalScore >= 7 && totalScore <= 12){
                            binding.tv412.setBackgroundColor(Color.parseColor("#FFE8F0"))
                            binding.tv5.setText("\n")}
                        else if (totalScore >= 13 && totalScore <= 18){
                            binding.tv413.setBackgroundColor(Color.parseColor("#FFE8F0"))
                            binding.tv5.setText("\n건강 관리에 주의가 필요합니다. ")}
                        else if (totalScore >= 19 && totalScore <= 24){
                            binding.tv414.setBackgroundColor(Color.parseColor("#FFE8F0"))
                            binding.tv5.setText("\n고위험 위험도가 높습니다. 병원 방문이 필요합니다.")}
                        else if (totalScore >= 19 && totalScore <= 24){
                            binding.tv415.setBackgroundColor(Color.parseColor("#FFE8F0"))
                            binding.tv5.setText("\n고위험 위험도가 매우 높습니다. 병원 방문이 필수입니다 !!")}

                        println("나이 $user_age 키 $height  기초대사량 $BMR 섭취 $eatCal 소모 $burntCal 테스트점수 $highTestScore 총점수 $totalScore")
                        binding.tv1.setText(
                            "- 섭취칼로리 : %d kcal     \n-소모 칼로리 : %.2f kcal \n-고위험 임신 테스트 점수 : %d점".format(
                                eatCal,
                                burntCal,
                                highTestScore
                            )
                        )
                        binding.tvScore.setText("$totalScore")
                    } else {

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    override fun onClick(v: View?) {
        dismiss()
    }
}
