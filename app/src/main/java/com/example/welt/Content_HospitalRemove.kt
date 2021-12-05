package com.example.welt

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.databinding.FragmentContentHospitalRemoveBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

val user = FirebaseAuth.getInstance().currentUser
val uid = user?.uid

class Content_HospitalRemove : DialogFragment() {

    private lateinit var binding: FragmentContentHospitalRemoveBinding
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
        binding = FragmentContentHospitalRemoveBinding.inflate(inflater, container, false)
        var target = "2021-12-06 17:00"
        var target_token = target.split(' ')
        var target_token_date = target_token[0].split('-')
        var target_dayOfWeek = calDayOfWeek("%s%s%s".format(target_token_date[0],target_token_date[1],target_token_date[2]))
        var target_viewText = "%s년 %s월 %s일 %s %s".format(target_token_date[0],target_token_date[1],target_token_date[2],target_dayOfWeek,target_token[1])

        binding.contentHospitalRemoveInfo.setText(target_viewText)
        binding.contentHospitalRemoveNo.setOnClickListener{
            dismiss()
        }
        binding.contentHospitalRemoveOK.setOnClickListener{
            if (uid != null) {
                myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("HospitalSchedule").child("$target_viewText")
                myRef.setValue(null)
                dismiss()
            }
        }
        return binding.root
    }
    // 무슨요일인지 계산
    fun calDayOfWeek(inputDate:String) :String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
        val date: Date = dateFormat.parse(inputDate)
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTime(date)
        var day = calendar.get(Calendar.DAY_OF_WEEK)
        if (day == 1)
            return "일요일"
        else if (day==2)
            return "월요일"
        else if (day==3)
            return "화요일"
        else if (day==4)
            return "수요일"
        else if (day==5)
            return "목요일"
        else if (day==6)
            return "금요일"
        else if (day==7)
            return "토요일"
        else
            return ""
    }

}