package com.example.welt.Mission

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate
import kotlin.math.pow

class Mission_burntCalDialog : DialogFragment(), View.OnClickListener {
    private lateinit var btnOK: Button
    private lateinit var content : TextView
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference

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
        val data : Map<String, Double> = mapOf("요가" to 2.5, "필라테스" to 2.5, "스트레칭" to 2.5, "수영" to 7.0, "자전거" to 8.0)
        var text : String = ""
        var totalCal = 0.0
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Health").child(date.toString())
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                databaseRef2 = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Weight")
                databaseRef2.limitToLast(1).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot2: DataSnapshot) {

                        if (!dataSnapshot.getValue().toString().equals("null") && !dataSnapshot.getValue().toString().equals("null") ){
                            val weight = dataSnapshot2.value.toString().substring(12,16).toDouble()
                            for (messageData in dataSnapshot.child("exercise").children) {
                                val cal = (data[messageData.key]!! * 3.5 * weight * messageData.getValue().toString().toDouble() * 5 ) / 1000
                                totalCal = totalCal + cal
                                text = text+ messageData.key +"  "+ messageData.getValue().toString() + "분  " + cal.toString() + "kcal\n\n"
                            }
                            text = text + "\n총 소모칼로리  " + totalCal.toString() + "kcal"
                            content.setText(text)
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })



            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onClick(v: View?) {
        dismiss()
    }


}