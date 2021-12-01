package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.utils.Utils.init
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Mission_babyMovementDialog : DialogFragment(), View.OnClickListener {
    private lateinit var btnOK: Button
    private lateinit var content : TextView
    private lateinit var databaseRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView =
            inflater.inflate(R.layout.fragment_mission_baby_movement_dialog, container, false) as ViewGroup


        btnOK = rootView.findViewById(R.id.btn_OK)
        content = rootView.findViewById(R.id.movementText)
        btnOK.setOnClickListener {
            dismiss()
        }
        init()

        return rootView
    }

    private fun init(){
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        var text : String = ""
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Health").child("20211127")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (messageData in dataSnapshot.child("baby_movment").children) {
                    text = text + messageData.child("start").getValue().toString() + " ~ " + messageData.child("end").getValue().toString() + "  태동 " + messageData.child("count").getValue().toString() + "번 \n\n"
                }
                content.setText(text)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onClick(v: View?) {
        dismiss()
    }

}