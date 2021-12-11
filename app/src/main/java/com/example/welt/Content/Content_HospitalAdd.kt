package com.example.welt.Content

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.Sign.myRef
import com.example.welt.databinding.FragmentContentHospitalAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Content_HospitalAdd : DialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentContentHospitalAddBinding
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
        binding = FragmentContentHospitalAddBinding.inflate(inflater, container, false)
        binding.contentHospitalAddOK.setOnClickListener{
            var selyear = binding.contentHospitalAddDpSpinner.year
            var selmonth = binding.contentHospitalAddDpSpinner.month+1
            var selday =binding.contentHospitalAddDpSpinner.dayOfMonth
            var selhour = binding.contentHospitalAddTpSpinner.hour
            var selmin = binding.contentHospitalAddTpSpinner.minute

            var selmonth_str = "00"
            var selday_str = "00"
            var selhour_str = "00"
            var selmin_str = "00"

            if (selmonth<10)
                selmonth_str="0$selmonth"
            else
                selmonth_str="$selmonth"
            if (selday<10)
                selday_str="0$selday"
            else
                selday_str="$selday"
            if (selhour<10)
                selhour_str="0$selhour"
            else
                selhour_str="$selhour"
            if (selmin<10)
                selmin_str="0$selmin"
            else
                selmin_str="$selmin"
            var dayOfWeek = calDayOfWeek("%s%s%s".format(selyear,selmonth_str,selday_str))
            var view_str = "%d년 %s월 %s일 %s %s:%s".format(selyear,selmonth_str,selday_str,dayOfWeek,selhour_str,selmin_str)
            var save_str= "$selyear-$selmonth_str-$selday_str $selhour_str:$selmin_str"
            var memo = binding.contentHospitalAddMemo.text
            binding.contentHospitalView.setText(view_str)
            Toast.makeText(context, "%s / %s \n 내원 일정이 저장되었습니다.".format(view_str,memo), Toast.LENGTH_LONG).show()
            if (uid != null) {
                myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("HospitalSchedule").child("$save_str")
                myRef.setValue("$memo")
            }
            dismiss()
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

    override fun onClick(v: View?) {
        dismiss()
    }
}