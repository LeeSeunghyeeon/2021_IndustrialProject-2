package com.example.welt.Mission

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.welt.R
import com.example.welt.databinding.FragmentMissionBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import kotlin.math.pow

class MissionFragment : Fragment() {
    private lateinit var binding:FragmentMissionBinding
    private var data:ArrayList<MyMission> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Mission_Adapter
    private lateinit var databaseRef:DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.now()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionBinding.inflate(inflater, container, false)

        initData()
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showItemList()
        showDialog()
    }

    private fun initData(){
        //수면 버튼 텍스트 변경
        var time = "0"
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Health").child(date.toString()).child("sleep")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.getValue().toString().equals("null")) time = dataSnapshot.child("hour").getValue().toString()
                binding.sleepDialogBtn.setText("수면\n"+time + "시간/8시간")
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        //검음수 버튼 텍스트 변경


        //섭취 칼로리 버튼 텍스트 변경


        //소모 칼로리 버튼 텍스트 변경
        var totalCal = 0.0
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Health").child(date.toString())
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.child("burntCal").getValue().toString().equals("null"))
                    totalCal = dataSnapshot.child("burntCal").getValue().toString().toDouble()

                binding.burntCalDialogBtn.setText("소모칼로리\n" + totalCal.toString() + " kcal")
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })


        //체중 칼로리 버튼 텍스트 변경
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Weight")
        databaseRef.limitToLast(1).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val temp = dataSnapshot.getValue().toString().split("=")
                val weight = temp[1].split("}")
                binding.weightDialogBtn.setText("체중\n"+weight[0]+"kg")

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })



        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Mission").child(date.toString())
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                for (messageData in dataSnapshot.children) {
                    /*val text = messageData.child("content").getValue().toString()
                    val cc = messageData.child("check").getValue().toString().toBoolean()
                    data.add(MyMission(text, cc))*/
                    val text = messageData.key.toString()
                    val cc = messageData.getValue().toString().toBoolean()
                    data.add(MyMission(text, cc))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        initRecyclerView()

    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = Mission_Adapter(data)

    }


    private fun showDialog(){
        binding.sleepDialogBtn.setOnClickListener{
            val sleepDialog = Mission_SleepDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                sleepDialog.show(fragmentManager, "Mission_SleepDialog")
            }
        }

        binding.walkDialogBtn.setOnClickListener{
            val walkDialog = Mission_walkDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                walkDialog.show(fragmentManager, "Mission_walkDialog")
            }
        }

        binding.eatCalDialogBtn.setOnClickListener{
            val eatCalDialog = Mission_eatCalDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                eatCalDialog.show(fragmentManager, "Mission_eatCalDialog")
            }
        }

        binding.burntCalDialogBtn.setOnClickListener{
            val burntCalDialog = Mission_burntCalDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                burntCalDialog.show(fragmentManager, "Mission_burntCalDialog")
            }
        }

        binding.weightDialogBtn.setOnClickListener{
            val weightDialog = Mission_weightDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                weightDialog.show(fragmentManager, "Mission_weightDialog")
            }
        }

        binding.babyMovementDialogBtn.setOnClickListener{
            val movementDialog = Mission_babyMovementDialog()
            activity?.supportFragmentManager?.let { fragmentManager ->
                movementDialog.show(fragmentManager, "Mission_babyMovementDialog")
            }
        }

    }

    fun showItemList() {
        adapter = Mission_Adapter(data)
        recyclerView.setAdapter(adapter)
    }

}