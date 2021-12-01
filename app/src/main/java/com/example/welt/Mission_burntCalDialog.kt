package com.example.welt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Mission_burntCalDialog : DialogFragment(), View.OnClickListener {
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
            inflater.inflate(R.layout.fragment_mission_burnt_cal_dialog, container, false) as ViewGroup
////////
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        init()
        btnOK = rootView.findViewById(R.id.btn_OK)
        content = rootView.findViewById(R.id.content)
        btnOK.setOnClickListener {
            dismiss()
        }

        return rootView
    }

    private fun init() {
        val data : Map<String, Double> = mapOf("yoga" to 2.5, "pilates" to 2.5, "stretch" to 2.5, "swimming" to 7.0, "bicycle" to 8.0)
        val name : Map<String, String> = mapOf("yoga" to "요가", "pilates" to "필라테스", "stretch" to "스트레칭", "swimming" to "수영", "bicycle" to "자전거")
        var text : String = ""
        var totalCal = 0.0
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Health").child("20211127")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val weight = dataSnapshot.child("weight").getValue().toString().toDouble()

                for (messageData in dataSnapshot.child("exercise").children) {
                    val cal = (data[messageData.key]!! * 3.5 * weight * messageData.getValue().toString().toDouble() * 5 ) / 1000
                    totalCal = totalCal + cal
                    text = text+ name[messageData.key] +"  "+ messageData.getValue().toString() + "분  " + cal.toString() + "kcal\n\n"
                }
                text = text + "\n총 소모칼로리  " + totalCal.toString() + "kcal"
                content.setText(text)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onClick(v: View?) {
        dismiss()
    }


}