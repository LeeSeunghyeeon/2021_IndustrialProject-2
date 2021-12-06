package com.example.welt.Health

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.welt.Mission.Mission_Adapter
import com.example.welt.Mission.MyMission
import com.example.welt.R
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthExerciseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.*
import java.time.LocalDate

class Health_exercise : DialogFragment() {
    private lateinit var binding: FragmentHealthExerciseBinding
    var myRef = database.getReference("User")
    private var data:ArrayList<MyMission> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Mission_Adapter


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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthExerciseBinding.inflate(inflater, container, false)

        // 스피너
        val spinner: Spinner = binding.spinnerExercise

        ArrayAdapter.createFromResource(
            requireContext(), R.array.exercise_arr, android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        var exerciseMap = mutableMapOf("요가" to 0, "필라테스" to 0, "스트레칭" to 0, "수영" to 0, "자전거" to 0)
        try{
            myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val test = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("exercise")
                    for (ds in test.children) {
                        exerciseMap.put(ds.key.toString(), Integer.parseInt(ds.value.toString()))
                    }
                    println(exerciseMap)
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", "에러")
                }
            })
        } catch(E:Exception) {

        }


        // 저장 버튼
        binding.BtnExerciseOK.setOnClickListener {

            try {
                // 입력한 운동 종목 가져오기
                var exercise = spinner.selectedItem.toString()
                // 입력한 운동 시간 가져오기
                var minute = Integer.parseInt(binding.exerciseInputMinute.getText().toString())

                if ((!exercise.equals("선택하세요")) && minute > 0) {
                    
                    // 원래 운동 종목 있으면 시간 추가
                    val beforeMinute = exerciseMap[exercise]
                    println(exercise + " 이전 시간: " + beforeMinute)
                    minute += Integer.parseInt(beforeMinute.toString())
                    println("이전 시간 + 추가된 시간: " + minute)

                    myRef.child(userID.toString()).child("Health").child(date.toString()).child("exercise").child(exercise).setValue(minute)

                    dismiss()


                } else {
                    Toast.makeText(getActivity(), "운동 종목과 운동 시간을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()
                }

            } catch (e:Exception) {
                Toast.makeText(getActivity(), "운동 종목과 운동 시간을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 삭제 버튼
        binding.BtnExerciseDelete.setOnClickListener{
            myRef = database.getReference("User")
            // 취소 시 실행할 코드
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            for(i in 0 until data.size){
                if(adapter.item_list[i].getSelected()){
                    val exercise_= adapter.item_list[i].text.split(" | ")
                    myRef.child(uid.toString()).child("Health").child(date.toString()).child("exercise").child(exercise_[0]).setValue(null)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        // 닫기 버튼
        binding.BtnExerciseCancle.setOnClickListener{
            // 취소 시 실행할 코드
            dismiss()
        }

        return binding.root
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = Mission_Adapter(data)
    }

    private fun initData() {

        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        myRef = myRef.child(uid.toString()).child("Health").child(date.toString()).child("exercise")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                for (messageData in dataSnapshot.children) {
                    /*val text = messageData.child("content").getValue().toString()
                    val cc = messageData.child("check").getValue().toString().toBoolean()
                    data.add(MyMission(text, cc))*/
                    val text = messageData.key.toString() + " | " + messageData.getValue().toString()+"분"
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
        adapter = Mission_Adapter(data)
        recyclerView.setAdapter(adapter)
    }
}