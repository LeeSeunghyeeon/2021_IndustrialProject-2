package com.example.welt.Health

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthBabyMovementBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception
import java.time.LocalDate


class Health_babyMovement : DialogFragment() {
    private lateinit var binding: FragmentHealthBabyMovementBinding
    val myRef = database.getReference("User")

    // 현재 user 가져오기
    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid // 현재 로그인한 사용자의 파이어베이스 uid

    // 현재 날짜 받아오기
    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHealthBabyMovementBinding.inflate(inflater, container, false)
        val startTP: TimePicker = binding.timePickerStart
        val endTP: TimePicker = binding.timePickerEnd

        // 저장 버튼
        binding.BtnBabymOK.setOnClickListener {
            // 시작 시간 값 가져오기
            var startHour = startTP.currentHour // 24시간 형식 (ex: 오후 3시 -> 15시)
            var startMinute = startTP.currentMinute
            var start = startHour.toString() + ":" + startMinute.toString()

            // 종료 시간 값 가져오기
            var endHour = endTP.currentHour
            var endMinute = endTP.currentMinute
            var end = endHour.toString() + ":" + endMinute.toString()

            // 횟수 가져오기
            try {
                var count = Integer.parseInt(binding.babyMCount.getText().toString())

                // 파이어베이스에 시작시간, 종료시간, 횟수 삽입
                myRef.child(userID.toString()).child("Health").child(date.toString()).child("baby_movement").child(start +","+end).setValue(count)
                
                // 작업 모두 끝나면 다이얼로그 창 닫기
                dismiss()
            } catch(E:Exception) {
                Toast.makeText(getActivity(), "태동 횟수를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            
        }

        // 닫기 버튼
        binding.BtnBabymcancle.setOnClickListener {
            dismiss()
        }

        return binding.root

    }

}
