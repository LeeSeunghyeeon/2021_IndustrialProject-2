package com.example.welt.Mission

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthExerciseBinding
import com.example.welt.databinding.FragmentMissionEatCalDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_health_meal.*
import kotlinx.android.synthetic.main.fragment_mission_eat_cal_dialog.*
import java.time.LocalDate

class Mission_eatCalDialog : DialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentMissionEatCalDialogBinding

    var myRef = database.getReference("User")

    // 현재 user 가져오기
    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid // 현재 로그인한 사용자의 파이어베이스 uid

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
        binding = FragmentMissionEatCalDialogBinding.inflate(inflater, container, false)
        var rootView =
            inflater.inflate(R.layout.fragment_mission_eat_cal_dialog, container, false) as ViewGroup
////////
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        try{
            myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val breakfastInfo = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("breakfastInfo").getValue()
                    val launchInfo = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("launchInfo").getValue()
                    val dinnerInfo = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("dinnerInfo").getValue()

                    if (breakfastInfo == null) {
                        binding.breakfastCal.setText("아침 식사 정보가 없습니다.")
                    } else {
                        binding.breakfastCal.setText(breakfastInfo.toString())
                    }
                    if (launchInfo == null) {
                        binding.launchCal.setText("점심 식사 정보가 없습니다.")
                    } else {
                        binding.launchCal.setText(launchInfo.toString())
                    }
                    if (dinnerInfo == null) {
                        binding.dinnerCal.setText("저녁 식사 정보가 없습니다.")
                    } else {
                        binding.dinnerCal.setText(dinnerInfo.toString())
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        } catch(E:Exception) {

        }


        binding.btnOK.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        dismiss()
    }

}