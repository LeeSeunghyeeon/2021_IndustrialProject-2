package com.example.welt

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.welt.databinding.FragmentContentHospitalBinding
import com.example.welt.databinding.FragmentContentWeekFeaturesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*



class Content_Hospital : Fragment() {
    private lateinit var binding: FragmentContentHospitalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentHospitalBinding.inflate(inflater, container, false)
        if (uid != null) {
            myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("HospitalSchedule")
            myRef.limitToLast(1)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var split1 = snapshot.value.toString().split("=")
                        var target = split1[0]
                        var target_token = target.split(' ')
                        var target_token_date0 = target_token[0].split('{')
                        var target_token_date = target_token_date0[1].split('-')
                        var target_token_time = target_token[1].split("=}")

                        var target_dayOfWeek = calDayOfWeek("%s%s%s".format(target_token_date[0],target_token_date[1],target_token_date[2]))
                        var target_viewText = "%s년 %s월 %s일 %s %s".format(target_token_date[0],target_token_date[1],target_token_date[2],target_dayOfWeek,target_token_time[0])
                        binding.contentHospitalSchedule.setText(target_viewText)
                        var target_memo = split1[1].split("}")
                        binding.contentHospitalMemo.setText(target_memo[0])
                    }
                    override fun onCancelled(error: DatabaseError) {
                        println("Failed")
                    }
        })
        }
        binding.HospitalOKbtn.setOnClickListener{
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().remove(Content_Hospital()).commit()
                fragmentManager.popBackStack()
            }
        }
        binding.hospitalAddBtn.setOnClickListener{
            val addHospitalScheduleDialog = Content_HospitalAdd()
            activity?.supportFragmentManager?.let { fragmentManager ->
                addHospitalScheduleDialog.show(fragmentManager, "Content_addHospitalDialog")
            }
        }

        binding.contentHospitalRemove.setOnClickListener{
            val removeHospitalScheduleDialog = Content_HospitalRemove()
            activity?.supportFragmentManager?.let { fragmentManager ->
                removeHospitalScheduleDialog.show(fragmentManager, "Content_removeHospitalDialog")
            }
        }

        binding.contentHospitalRevise.setOnClickListener{
            val reviseHospitalScheduleDialog = Content_HospitalRevise()
            activity?.supportFragmentManager?.let { fragmentManager ->
                reviseHospitalScheduleDialog.show(fragmentManager, "Content_reviseHospitalDialog")
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