package com.example.welt.Health

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthExerciseBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.*
import java.time.LocalDate

class Health_exercise : DialogFragment() {
    private lateinit var binding: FragmentHealthExerciseBinding
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
        binding = FragmentHealthExerciseBinding.inflate(inflater, container, false)
        // 현재 날짜 받아오기


        // 스피너
        val spinner: Spinner = binding.spinnerExercise

        ArrayAdapter.createFromResource(
            requireContext(), R.array.exercise_arr, android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // 저장 버튼
        binding.BtnExerciseOK.setOnClickListener {

            try {
                // 운동 종목 가져오기
                var exercise = spinner.selectedItem.toString()
                // 운동 시간 가져오기
                var minute = Integer.parseInt(binding.exerciseInputMinute.getText().toString())

                if ((!exercise.equals("선택하세요")) && minute > 0) {
                    // 파이어베이스에 운동 종목, 운동 시간 저장
                    myRef.child(userID.toString()).child("Health").child(date.toString()).child("exercise").child(exercise).setValue(minute)
                    dismiss()
                } else {
                    Toast.makeText(getActivity(), "운동 종목과 운동 시간을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()
                }

            } catch (e:Exception) {
                Toast.makeText(getActivity(), "운동 시간을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 삭제 버튼
        binding.BtnExerciseDelete.setOnClickListener{
            // 취소 시 실행할 코드
            dismiss()
        }

        // 닫기 버튼
        binding.BtnExerciseCancle.setOnClickListener{
            // 취소 시 실행할 코드
            dismiss()
        }

        return binding.root
    }
}