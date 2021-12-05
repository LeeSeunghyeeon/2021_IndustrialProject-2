package com.example.welt.Mission

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class Mission_SleepDialog : DialogFragment(), View.OnClickListener {
    var chart: PieChart? = null
    private lateinit var btnOK: Button
    private lateinit var textMessage : TextView
    private lateinit var databaseRef: DatabaseReference
    var sleep : Int ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView =
            inflater.inflate(R.layout.fragment_mission__sleep_dialog, container, false) as ViewGroup
////////
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        btnOK = rootView.findViewById(R.id.btn_OK)
        textMessage = rootView.findViewById(R.id.sleepFeedbackText)
        btnOK.setOnClickListener {
            dismiss()
        }

        initUI(rootView)
        return rootView
    }

    override fun onClick(v: View?) {
        dismiss()
    }

    private fun initUI(rootView: ViewGroup) {
        chart = rootView.findViewById(R.id.piechart)
        chart!!.setUsePercentValues(true)
        chart!!.description.isEnabled = false

        chart!!.setTransparentCircleColor(Color.WHITE)
        chart!!.setTransparentCircleAlpha(110)

        chart!!.holeRadius = 58f
        chart!!.transparentCircleRadius = 61f

        chart!!.setDrawCenterText(true)

        chart!!.isHighlightPerTapEnabled = true

        val legent1 = chart!!.legend
        legent1.isEnabled = false

        chart!!.setEntryLabelColor(Color.WHITE)
        chart!!.setEntryLabelTextSize(12f)

        setData()

    }

    private fun setData(){
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Health").child("20211127").child("sleep")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.getValue().toString().toInt() > 8){
                    textMessage.setText("잠이 충분합니다. \n 오늘도 좋은 하루 보내세요.")
                }else if(dataSnapshot.getValue().toString().toInt() < 8){
                    val feedback = 8 - dataSnapshot.getValue().toString().toInt()
                    textMessage.setText("권장 수면 시간보다 " + feedback.toString() + "시간이 부족합니다.")
                }
                val sleepTime_str = (dataSnapshot.getValue().toString().toFloat() / 8.0F) *100.0
                val sleepTime = sleepTime_str.toFloat()


                val entries = ArrayList<PieEntry>()
                entries.add(
                    PieEntry(
                        sleepTime, dataSnapshot.getValue().toString(),
                        ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_baseline_bedtime_24, null)
                    )

                )
                entries.add(
                    PieEntry(
                        100-sleepTime, "",
                        ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_baseline_wb_sunny_24, null)
                    )

                )

                val dataSet = PieDataSet(entries, "수면 시간")
                dataSet.setDrawIcons(true)
                dataSet.sliceSpace = 3f
                dataSet.iconsOffset = MPPointF(0F, (-40).toFloat())
                dataSet.selectionShift = 5f
                val colors = ArrayList<Int>()
                colors.add(Color.rgb(175,196,231))
                colors.add(Color.rgb(238, 175,175))
                dataSet.colors = colors
                val data = PieData(dataSet)
                data.setValueTextSize(22.0f)
                data.setValueTextColor(Color.WHITE)
                chart!!.data = data
                chart!!.invalidate()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })



    }


}