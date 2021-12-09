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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.welt.Mission.MyMission
import com.example.welt.R
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthMedicineManageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Health_medicineManage : DialogFragment() {
    private lateinit var binding: FragmentHealthMedicineManageBinding
    var myRef = database.getReference("User")
    var myRef2 = database.getReference("User")
    var myRef3 = database.getReference("User")
    private var data:ArrayList<MyMission> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Health_Adapter

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showItemList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthMedicineManageBinding.inflate(inflater, container, false)
        initData()

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
            myRef = database.getReference("User")
            try {
                val medicineName = binding.medicineName.getText().toString() // 약 이름
                var Mcount = countSpinner.selectedItem.toString()
                var Mwhen = whenSpinner.selectedItem.toString()

                if (medicineName.length > 0 && (!Mcount.equals("선택")) && (!Mwhen.equals("선택"))){
                    // 파이어베이스 약 이름, 횟수, 언제 먹을지 저장
                    myRef.child(userID.toString()).child("Medicine").child(medicineName).child("count").setValue(Mcount)
                    myRef.child(userID.toString()).child("Medicine").child(medicineName).child("when").setValue(Mwhen)

                } else {
                    Toast.makeText(getActivity(), "모든 항목을 입력해 주셔야 합니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "모든 항목을 입력해 주셔야 합니다.", Toast.LENGTH_SHORT).show()
            }

            val currentDate: LocalDateTime = LocalDateTime.now()
            val cal_today = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))

            myRef = database.getReference("User").child(userID.toString()).child("UserInfo")
            myRef.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val baby_birth = snapshot.child("user_babyBirth").getValue() //출산예정일

                    val startDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(cal_today.toString())
                    val endDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(baby_birth.toString())
                    val remaining_days = (endDate.time - startDate.time) / (24 * 60 * 60 * 1000)
                    myRef2=database.getReference("User").child(userID.toString()).child("Mission")
                    for(i in 0..remaining_days){
                        myRef2.child(date.plusDays(i).toString()).child(binding.medicineName.getText().toString()+" 복용하기").setValue(false)
                    }


                }
                override fun onCancelled(error: DatabaseError) {
                    println("Failed")
                }
            })

        }


        // 체크된 약 삭제하기 
        binding.DeleteMedicine.setOnClickListener {
            // 파이어베이스에서 데이터 삭제
            myRef = database.getReference("User")
            // 취소 시 실행할 코드
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            for(i in 0 until data.size){
                if(adapter.item_list[i].getSelected()){
                    val exercise_= adapter.item_list[i].text.split(" | ")
                    myRef.child(uid.toString()).child("Medicine").child(exercise_[0]).setValue(null)
                    adapter.notifyDataSetChanged()

                    val currentDate: LocalDateTime = LocalDateTime.now()
                    val cal_today = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))

                    myRef3 = database.getReference("User").child(userID.toString()).child("UserInfo")
                    myRef3.addValueEventListener(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val baby_birth = snapshot.child("user_babyBirth").getValue() //출산예정일

                            val startDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(cal_today.toString())
                            val endDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(baby_birth.toString())
                            val remaining_days = (endDate.time - startDate.time) / (24 * 60 * 60 * 1000)
                            myRef2=database.getReference("User").child(userID.toString()).child("Mission")
                            for(i in 0..remaining_days){
                                myRef2.child(date.plusDays(i).toString()).child(exercise_[0]+" 복용하기").setValue(null)
                            }

                        }
                        override fun onCancelled(error: DatabaseError) {
                            println("Failed")
                        }
                    })
                }
            }
            Toast.makeText(getActivity(), "해당 복약 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show()

        }

        // 닫기 버튼
        binding.CancleMedicine.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = Health_Adapter(data)
    }

    private fun initData() {

        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        myRef = myRef.child(uid.toString()).child("Medicine")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                for (messageData in dataSnapshot.children) {
                    val text = messageData.key.toString() + " | " + messageData.child("when").getValue().toString()+ " | "+messageData.child("count").getValue().toString()
                    val cc = false
                    data.add(MyMission(text, cc))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        initRecyclerView()
    }

    fun showItemList() {
        adapter = Health_Adapter(data)
        recyclerView.setAdapter(adapter)
    }
}