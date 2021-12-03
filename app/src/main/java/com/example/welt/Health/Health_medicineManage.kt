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
import com.example.welt.databinding.FragmentHealthMedicineManageBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception
import java.time.LocalDate

class Health_medicineManage : DialogFragment() {
    private lateinit var binding: FragmentHealthMedicineManageBinding
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
        binding = FragmentHealthMedicineManageBinding.inflate(inflater, container, false)

        val countSpinner: Spinner = binding.spinnerCount // 횟수 스피너
        val whenSpinner: Spinner = binding.spinnerWhen // 식전 식후 스피너

        ArrayAdapter.createFromResource(
            requireContext(), R.array.countOfMedicine, android.R.layout.simple_spinner_item
        ).also{ adapter2 ->
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            countSpinner.adapter = adapter2
        }

        ArrayAdapter.createFromResource(
            requireContext(), R.array.before_after, android.R.layout.simple_spinner_item
        ).also{ adapter3 ->
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            whenSpinner.adapter = adapter3
        }

        // 입력한 복약 내역 저장
        binding.BtnBabymOK.setOnClickListener {
            try {
                val medicineName = binding.medicineName.getText().toString() // 약 이름
                var Mcount = countSpinner.selectedItem.toString()
                var Mwhen = whenSpinner.selectedItem.toString()

                if (medicineName.length > 0 && (!Mcount.equals("선택")) && (!Mwhen.equals("선택"))){
                    // 파이어베이스 약 이름, 횟수, 언제 먹을지 저장
                    myRef.child(userID.toString()).child("Health").child(date.toString()).child("medicine").child(medicineName).child("count").setValue(Mcount)
                    myRef.child(userID.toString()).child("Health").child(date.toString()).child("medicine").child(medicineName).child("when").setValue(Mwhen)

                    myRef.child(userID.toString()).child("Medicine").child(date.toString()).child(medicineName).child("count").setValue(Mcount)
                    myRef.child(userID.toString()).child("Medicine").child(date.toString()).child(medicineName).child("when").setValue(Mwhen)
                    dismiss()
                } else {
                    Toast.makeText(getActivity(), "모든 항목을 입력해 주셔야 합니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "모든 항목을 입력해 주셔야 합니다.", Toast.LENGTH_SHORT).show()
            }

        }


        // 체크된 약 삭제하기 
        binding.DeleteMedicine.setOnClickListener {
            // 파이어베이스에서 데이터 삭제
            Toast.makeText(getActivity(), "해당 복약 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }

        // 닫기 버튼
        binding.CancleMedicine.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}