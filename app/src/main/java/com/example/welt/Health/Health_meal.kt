package com.example.welt.Health

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthMealBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_health_meal.*
import java.lang.Exception
import java.time.LocalDate


class Health_meal : DialogFragment() {
    private lateinit var binding: FragmentHealthMealBinding
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
        binding = FragmentHealthMealBinding.inflate(inflater, container, false)

        try {
            myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val meal1 = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("breakfast").getValue()
                    val meal2 = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("launch").getValue()
                    val meal3 = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("dinner").getValue()
                    if (meal1 == null) {
                        binding.inputBreakfast.setHint("아침 메뉴를 입력하세요.")
                    } else {
                        binding.inputBreakfast.setText(meal1.toString())
                    }
                    if (meal2 == null) {
                        binding.inputLaunch.setHint("점심 메뉴를 입력하세요.")
                    } else {
                        binding.inputLaunch.setText(meal2.toString())
                    }
                    if (meal3 == null) {
                        binding.inputDinner.setHint("저녁 메뉴를 입력하세요.")
                    } else {
                        binding.inputDinner.setText(meal3.toString())
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", "에러")
                }
            })

        }catch (E:Exception) {
        }

        // 아침 메뉴 저장
        binding.breakfastOK.setOnClickListener {
            try {
                var breakfast = input_breakfast.text.toString()
                insertDB("breakfast", breakfast)
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        // 아침 메뉴 삭제
        binding.breakfastDelete.setOnClickListener {
            deleteDB("breakfast")
            binding.inputBreakfast.setText("")
        }

        // 점심 메뉴 저장
        binding.launchOK.setOnClickListener {
            try {
                var launch = input_launch.text.toString()
                insertDB("launch", launch)
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        // 점심 메뉴 삭제
        binding.launchDelete.setOnClickListener {
            deleteDB("launch")
            binding.inputLaunch.setText("")
        }

        // 저녁 메뉴 저장
        binding.dinnerOK.setOnClickListener {
            try {
                var dinner = input_dinner.text.toString()
                insertDB("dinner", dinner)
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 저녁 메뉴 삭제
        binding.dinnerDelete.setOnClickListener {
            deleteDB("dinner")
            binding.inputDinner.setText("")
        }

        // 확인
        binding.BtnMealOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun insertDB(timeOfMeal: String, setMenu: String) {
        if (setMenu.length > 0) {
            // 저장
            myRef.child(userID.toString()).child("Health").child(date.toString()).child("meal").child(timeOfMeal).setValue(setMenu)
        } else {
            // 저장 X
            Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteDB(timeOfMeal: String) {
        try {
            myRef.child(userID.toString()).child("Health").child(date.toString()).child("meal").child(timeOfMeal).setValue(null)
        } catch (E:Exception) {
        }
    }


}


