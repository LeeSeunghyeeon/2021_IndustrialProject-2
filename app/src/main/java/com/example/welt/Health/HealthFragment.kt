package com.example.welt.Health

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

// 몸무게랑 복약은 그대로

// 식사 기록 가져오기 예시
//        myRef.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val test = snapshot.child("1").child("Health").child("20211127").child("meal")
//                for (ds in test.children) {
//                    Log.d("snap", ds.toString())
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("error", "에러")
//            }
//        })

class HealthFragment : Fragment() {
    private lateinit var binding: FragmentHealthBinding
    val myRef = database.getReference("User")

    // 현재 user 가져오기
    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid

    // 현재 날짜 받아오기
    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.now()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHealthBinding.inflate(inflater, container, false)
        setButtonClickEvent()
        return binding.root


    }
    
    private fun setButtonClickEvent(){
        
        // 운동
        binding.BtnExercise.setOnClickListener{
            val dialog = Health_exercise()
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager, "Health_exercise_dialog")
            }
        }

        // 식사
        binding.BtnMeal.setOnClickListener{
            val dialog = Health_meal()
            //dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_NoTitleBar_Fullscreen)
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager, "Health_meal_dialog")
            }
        }

        // 수면
        binding.BtnSleep.setOnClickListener{
            val dialog = Health_sleep()
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager, "Health_sleep_dialog")
            }
        }

        // 체중
        binding.BtnWeight.setOnClickListener{
            val dialog = Health_weight()
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager, "Health_weight_dialog")
            }
        }

        // 태동
        binding.BtnBabyMovement.setOnClickListener{
            val dialog = Health_babyMovement()
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager, "Health_babyMovement_dialog")
            }
        }

        // 복약 (프래그먼트 - 프래그먼트)
        binding.BtnMedicineManage.setOnClickListener{
            val dialog = Health_medicineManage()
            activity?.supportFragmentManager?.let { fragmentManager ->
                dialog.show(fragmentManager, "Health_medicineManage_dialog")
            }
        }
    }

}