package com.example.welt

import android.content.Context
import android.graphics.ColorSpace
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.welt.databinding.FragmentMissionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener

class MissionFragment : Fragment() {
    private lateinit var binding:FragmentMissionBinding
    private var data:ArrayList<MyMission> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Mission_Adapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var databaseRef:DatabaseReference
    private lateinit var uid : FirebaseAuth


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
        data.clear()
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Mission").child("20211124")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (messageData in dataSnapshot.children) {
                    val text = messageData.child("content").getValue().toString()
                    val cc = messageData.child("check").getValue().toString().toBoolean()
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

        /*adapter.itemClickListener = object : Mission_Adapter.OnItemClickListener,
            AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }

        }*/
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