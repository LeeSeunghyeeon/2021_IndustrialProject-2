package com.example.welt.Content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.welt.databinding.FragmentContentBinding
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.welt.Mission.MyMission
import com.example.welt.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding
    private lateinit var myRef: DatabaseReference

    var week = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        initData()
        calHigh()

        setButtonClickEvent()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initData() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        val currentDate: LocalDateTime = LocalDateTime.now() //오늘 날짜
        val show_today =
            currentDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일", Locale("ko", "KR")))
        val cal_today =
            currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))
        var babyName = ""
        binding.contentTodaydate.setText("오늘은 $show_today")
        if (uid != null) {
            myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString())
                .child("UserInfo")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    babyName = snapshot.child("user_baby_name").getValue().toString() // 태명
                    val baby_birth = snapshot.child("user_babyBirth").getValue() //출산예정일
                    binding.contentBabybirth.setText(
                        "출산예정일 | %s년 %s월 %s일".format(
                            baby_birth.toString().substring(0, 4),
                            baby_birth.toString().substring(4, 6),
                            baby_birth.toString().substring(6, 8)
                        )
                    )
                    val startDate =
                        SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(cal_today.toString())
                    val endDate = SimpleDateFormat(
                        "yyyyMMdd",
                        Locale("ko", "KR")
                    ).parse(baby_birth.toString())
                    val remaining_days = (endDate.time - startDate.time) / (24 * 60 * 60 * 1000)
                    binding.contentRemainingDays.setText("$remaining_days")
                    week = 41 - (remaining_days / 7).toInt()
                    binding.contentShowWeek.setText("%d주차".format(week))
                    myRef =
                        FirebaseDatabase.getInstance().getReference("User").child(uid.toString())
                            .child("UserInfo")
                    myRef.child("week").setValue(week.toString())
                    if (week >= 4 && week <= 40) {
                        myRef = FirebaseDatabase.getInstance().getReference("WeekInfo")
                        myRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var babyHeight =
                                    snapshot.child("%d주차".format(week)).child("babyHeight")?.value
                                var babyWeight =
                                    snapshot.child("%d주차".format(week)).child("babyWeight")?.value
                                val fruitName = snapshot.child("%d주차".format(week))
                                    .child("fruitName")?.value.toString()
                                binding.contentWeekSize.setText(
                                    "%s(이)는 현재 \n%s 크기입니다.".format(
                                        babyName,
                                        fruitName
                                    )
                                )
                                binding.contentWeekBody.setText(
                                    "(%s , %s)".format(
                                        babyHeight,
                                        babyWeight
                                    )
                                )
                                binding.contentMoreBtn.isVisible=true
                                if (week == 4) binding.contentWeekPhoto.setImageResource(R.drawable.week04_poppyseed)
                                else if (week == 5) binding.contentWeekPhoto.setImageResource(R.drawable.week05_appleseed)
                                else if (week == 6) binding.contentWeekPhoto.setImageResource(R.drawable.week06_bean)
                                else if (week == 7) binding.contentWeekPhoto.setImageResource(R.drawable.week07_blueberry)
                                else if (week == 8) binding.contentWeekPhoto.setImageResource(R.drawable.week08_raspberries)
                                else if (week == 9) binding.contentWeekPhoto.setImageResource(R.drawable.week09_olive)
                                else if (week == 10) binding.contentWeekPhoto.setImageResource(R.drawable.week10_prune)
                                else if (week == 11) binding.contentWeekPhoto.setImageResource(R.drawable.week11_lime)
                                else if (week == 12) binding.contentWeekPhoto.setImageResource(R.drawable.week12_plum)
                                else if (week == 13) binding.contentWeekPhoto.setImageResource(R.drawable.week13_peach)
                                else if (week == 14) binding.contentWeekPhoto.setImageResource(R.drawable.week14_lemon)
                                else if (week == 15) binding.contentWeekPhoto.setImageResource(R.drawable.week15_oranges)
                                else if (week == 16) binding.contentWeekPhoto.setImageResource(R.drawable.week16_avocado)
                                else if (week == 17) binding.contentWeekPhoto.setImageResource(R.drawable.week17_onion)
                                else if (week == 18) binding.contentWeekPhoto.setImageResource(R.drawable.week18_sweet_potato)
                                else if (week == 19) binding.contentWeekPhoto.setImageResource(R.drawable.week19_mango)
                                else if (week == 20) binding.contentWeekPhoto.setImageResource(R.drawable.week20_banana)
                                else if (week == 21) binding.contentWeekPhoto.setImageResource(R.drawable.week21_pomegranate)
                                else if (week == 22) binding.contentWeekPhoto.setImageResource(R.drawable.week22_papaya)
                                else if (week == 23) binding.contentWeekPhoto.setImageResource(R.drawable.week23_grapefruit)
                                else if (week == 24) binding.contentWeekPhoto.setImageResource(R.drawable.week24_melon)
                                else if (week == 25) binding.contentWeekPhoto.setImageResource(R.drawable.week25_cauliflower)
                                else if (week == 26) binding.contentWeekPhoto.setImageResource(R.drawable.week26_lettuce)
                                else if (week == 27) binding.contentWeekPhoto.setImageResource(R.drawable.week27_turnip)
                                else if (week == 28) binding.contentWeekPhoto.setImageResource(R.drawable.week28_aubergine)
                                else if (week == 29) binding.contentWeekPhoto.setImageResource(R.drawable.week29_acorn_pumpkin)
                                else if (week == 30) binding.contentWeekPhoto.setImageResource(R.drawable.week30_cucumber)
                                else if (week == 31) binding.contentWeekPhoto.setImageResource(R.drawable.week31_pineapple)
                                else if (week == 32) binding.contentWeekPhoto.setImageResource(R.drawable.week32_pumpkin)
                                else if (week == 33) binding.contentWeekPhoto.setImageResource(R.drawable.week33_durian)
                                else if (week == 34) binding.contentWeekPhoto.setImageResource(R.drawable.week34_squash)
                                else if (week == 35) binding.contentWeekPhoto.setImageResource(R.drawable.week35_coconut)
                                else if (week == 36) binding.contentWeekPhoto.setImageResource(R.drawable.week36_lettuce)
                                else if (week == 37) binding.contentWeekPhoto.setImageResource(R.drawable.week37_beetroot)
                                else if (week == 38) binding.contentWeekPhoto.setImageResource(R.drawable.week38_pumpkin)
                                else if (week == 39) binding.contentWeekPhoto.setImageResource(R.drawable.week39_watermelon)
                                else if (week == 40) binding.contentWeekPhoto.setImageResource(R.drawable.week40_jackfruit)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                println("Failed")
                            }
                        })
                    } else { //4주 미만일 때
                        binding.contentMoreBtn.isVisible=false
                        binding.contentWeekSize.setText("")
                        binding.contentWeekBody.setText("")
                        binding.contentWeekPhoto.setImageResource(R.drawable.week00_null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed")
                }
            })
        }
        if (com.example.welt.Content.uid != null) {
            myRef = FirebaseDatabase.getInstance().getReference("User")
                .child(com.example.welt.Content.uid.toString())
                .child("HospitalSchedule")
            myRef.addValueEventListener(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    var min = 1000
                    for (messageData in snapshot.children) {
                        var split = messageData.key.toString().split(" ")
                        var split2 = split[0].split("-")
                        var hospitaldate = "%s%s%s".format(split2[0], split2[1], split2[2])
                        val currentDate: LocalDateTime = LocalDateTime.now() //오늘 날짜
                        val cal_today = currentDate.format(
                            DateTimeFormatter.ofPattern(
                                "yyyyMMdd",
                                Locale("ko", "KR")
                            )
                        )
                        val startDate = SimpleDateFormat(
                            "yyyyMMdd",
                            Locale("ko", "KR")
                        ).parse(cal_today.toString())
                        val endDate = SimpleDateFormat(
                            "yyyyMMdd",
                            Locale("ko", "KR")
                        ).parse(hospitaldate.toString())
                        val remaining_days =
                            (endDate.time - startDate.time) / (24 * 60 * 60 * 1000)
                        if (remaining_days.toInt() >= 0 && min > remaining_days.toInt())
                            min = remaining_days.toInt()
                    }
                    if (min == 1000)
                        binding.contentHospitalBtn.setText("🏥\n다음 내원 일정\n없음 ")
                    else if (min == 0)
                        binding.contentHospitalBtn.setText("🏥\n내원일 \nD-day")
                    else
                        binding.contentHospitalBtn.setText("🏥\n내원일\nD-%d".format(min))
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setButtonClickEvent() {
        binding.contentYoutubeBtn.setOnClickListener {
            var intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/results?search_query=$week%EC%A3%BC%EC%B0%A8+%ED%83%9C%EA%B5%90+")
            )
            startActivity(intent)
        }
        binding.contentDepressionBtn.setOnClickListener {

            var intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.gimpo.go.kr/health/contents.do?key=2196")
            )
            startActivity(intent)
        }
        binding.contentMoreBtn.setOnClickListener {
            myRef = FirebaseDatabase.getInstance().getReference("WeekInfo")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (week <= 40 && week >= 4) {
                        var link_name =
                            snapshot.child("%d주차".format(week)).child("link_name")?.value.toString()
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$link_name"))
                        startActivity(intent)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed")
                }
            })
        }
        binding.contentHighBtn.setOnClickListener {
            val show_Content_HighRiskPregnancyTest =
                activity?.supportFragmentManager?.beginTransaction()

            if (show_Content_HighRiskPregnancyTest != null) {
                show_Content_HighRiskPregnancyTest.replace(
                    R.id.frameLayout_content,
                    Content_HighRiskPregnancyTest()
                ).addToBackStack(null).commit()
            }
        }

        binding.contentHospitalBtn.setOnClickListener {
            val show_hospital = activity?.supportFragmentManager?.beginTransaction()
            if (show_hospital != null) {
                show_hospital.replace(R.id.frameLayout_content, Content_Hospital())
                    .addToBackStack(null).commit()
            }
        }

        binding.btnView.setOnClickListener{
            val highRiskViewDialog = Content_HighRiskView()
            activity?.supportFragmentManager?.let { fragmentManager ->
                highRiskViewDialog.show(fragmentManager, "Content_highRiskViewDialog")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calHigh() {
        if (uid != null) {
            myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString())
            myRef.addValueEventListener(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentDate: LocalDateTime = LocalDateTime.now() //오늘 날짜
                    val today = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))
                    var date = "%s-%s-%s".format(today.substring(0, 4), today.substring(4, 6), today.substring(6, 8))
                    if (snapshot.child("HighTest").child("score").value != null
                        && snapshot.child("Health").child("$date").child("meal").child("breakfastCal").value != null
                        && snapshot.child("Health").child("$date").child("meal").child("launchCal").value != null
                        && snapshot.child("Health").child("$date").child("meal").child("dinnerCal").value != null
                        && snapshot.child("Health").child("$date").child("burntCal").value != null) {
                            binding.btnView.isVisible = true
                            var totalScore = 0
                        var eatCal = snapshot.child("Health").child("$date").child("meal").child("breakfastCal").value.toString().toDouble()+snapshot.child("Health").child("$date").child("meal").child("launchCal").value.toString().toDouble()+snapshot.child("Health").child("$date").child("meal").child("dinnerCal").value.toString().toDouble()
                        var personal_burntCal = snapshot.child("Health").child("$date")
                            .child("burntCal").value.toString().toDouble()
                        var highTestScore =
                            snapshot.child("HighTest").child("score").value.toString().toInt()
                        var height =
                            snapshot.child("UserInfo").child("height").value.toString().toDouble()
                        val splitarr = snapshot.child("Weight").value.toString().split(", ")
                        var weight = splitarr[splitarr.size - 1].split("=")[1].split("}")[0].toDouble()

                        var week = snapshot.child("UserInfo").child("week").value.toString().toInt()
                        val user_age = calculateAge(SimpleDateFormat(
                            "yyyyMMdd",
                            Locale("ko", "KR")).parse(snapshot.child("UserInfo").child("user_birth").getValue().toString()
                        ))
                        var BMR = 655.1 +(9.56*weight)+(1.85*height)-(4.68*user_age)
                        var recommendedCal = 0
                        if (week >= 1 && week <= 12) {
                            recommendedCal = 2100
                            BMR+=300
                        }
                        else if (week >= 13 && week <= 28) {
                            recommendedCal = 2340
                            BMR+=400
                        }
                        else {
                            recommendedCal = 2451
                            BMR+=500
                        }
                        var burntCal = eatCal-BMR-personal_burntCal

                        if (eatCal <= (recommendedCal + recommendedCal * 0.1) && eatCal >= (recommendedCal - recommendedCal * 0.1))
                            totalScore += 1
                        else if (eatCal < (recommendedCal + recommendedCal * 0.15) && eatCal > (recommendedCal + recommendedCal * 0.1))
                            totalScore += 3
                        else if (eatCal > (recommendedCal - recommendedCal * 0.15) && eatCal < (recommendedCal - recommendedCal * 0.1))
                            totalScore += 3
                        else if (eatCal < (recommendedCal + recommendedCal * 0.2) && eatCal > (recommendedCal + recommendedCal * 0.15))
                            totalScore += 5
                        else if (eatCal > (recommendedCal - recommendedCal * 0.2) && eatCal < (recommendedCal - recommendedCal * 0.15))
                            totalScore += 5
                        else
                            totalScore += 7

                        if(burntCal>=-200 && burntCal<=200)
                            totalScore+=1
                        else if(burntCal<=200 && burntCal>400)
                            totalScore+=3
                        else if(burntCal>=-400 && burntCal<-200)
                            totalScore+=3
                        else if(burntCal<=400 && burntCal>600)
                            totalScore+=5
                        else if(burntCal>=600 && burntCal<-400)
                            totalScore+=5
                        else
                            totalScore+=7

                        if(highTestScore>=0 && highTestScore<40)
                            totalScore+=1
                        else if (highTestScore>=40 && highTestScore<60)
                            totalScore+=7
                        else if (highTestScore>=60 && highTestScore<80)
                            totalScore+=10
                        else if (highTestScore>=80)
                            totalScore+=20

                        var state = " "
                        if(totalScore<=6)
                            state = "매우 양호👍"
                        else if(totalScore>=7 && totalScore<=12)
                            state = "양호👌"
                        else if(totalScore>=13 && totalScore<=18)
                            state = "주의⚠"
                        else if(totalScore>=19 && totalScore<=24)
                            state = "경고❗"
                        else if(totalScore>=19 && totalScore<=24)
                            state = "위험‼"
                        binding.contentHighscore.setText("$totalScore")
                        binding.contentHighstate.setText("$state")
                        binding.content25score.setText("  / 25점")
                    }
                    else {
                        binding.btnView.isVisible = false
                        binding.content25score.setText("점수를 불러 올 수 없습니다.")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
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
}
