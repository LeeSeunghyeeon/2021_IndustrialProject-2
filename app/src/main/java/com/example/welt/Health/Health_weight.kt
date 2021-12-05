package com.example.welt.Health

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthWeightBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception
import java.time.LocalDate

class Health_weight : DialogFragment() {

    private lateinit var binding:FragmentHealthWeightBinding

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
        binding = FragmentHealthWeightBinding.inflate(inflater, container, false)

        // 저장
        binding.BtnWeightOK.setOnClickListener {

            try {
                var weight = binding.weight.getText().toString().format("%.1f").toDouble()

                if (weight > 0) {
                    // 날짜에 맞게 파이어베이스에 저장
                    myRef.child(userID.toString()).child("Health").child(date.toString()).child("weight").setValue(weight)
                    myRef.child(userID.toString()).child("Weight").child(date.toString()).setValue(weight)
                } else {
                    Toast.makeText(getActivity(), "몸무게를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
                }

                dismiss()

            } catch (e:Exception){
                Toast.makeText(getActivity(), "몸무게를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

        binding.BtnWeightCancel.setOnClickListener{
            dismiss()
        }

        return binding.root
    }

}

