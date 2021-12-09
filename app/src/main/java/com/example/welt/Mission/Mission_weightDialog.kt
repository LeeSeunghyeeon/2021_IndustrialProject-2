package com.example.welt.Mission

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate
import java.util.*
import kotlin.math.pow

class Mission_weightDialog : DialogFragment(), View.OnClickListener {
    var chart :LineChart?= null
    private lateinit var btnOK:Button
    private lateinit var content : TextView
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_mission_weight_dialog, container, false) as ViewGroup

        btnOK = rootView.findViewById(R.id.btn_OK)
        content = rootView.findViewById(R.id.weightFeedbackText)
        btnOK.setOnClickListener{
            dismiss()
        }
        initContent()

        val values = ArrayList<Entry>()
        val xAxisValues = ArrayList<String>()
        var count = 1f
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Weight")
        databaseRef.limitToLast(5).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var last = 50f
                for (messageData in dataSnapshot.children) {
                    xAxisValues.add(messageData.key.toString().substring(5))
                    values.add(Entry(count*24f, messageData.getValue().toString().toFloat()))
                    count = count+1f
                    last = messageData.getValue().toString().toFloat()

                }
                if(count != 6f){
                    for(i in count.toInt() until 6f.toInt()){
                        values.add(Entry(i*24f, last))
                        xAxisValues.add("--")
                    }
                }
                initUI(rootView, xAxisValues)
                setData(values)

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        initUI(rootView, xAxisValues)
        return rootView
    }

    private fun initContent() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        var bmi = 0.0

        databaseRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("Weight")
        databaseRef.limitToLast(1).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                databaseRef2 = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("UserInfo")
                databaseRef2.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot2: DataSnapshot) {

                        if (!dataSnapshot.getValue().toString().equals("null")) {
                            val temp = dataSnapshot.getValue().toString().split("=")
                            val weight = temp[1].split("}")
                            bmi = weight[0].toDouble().div(dataSnapshot2.child("height").getValue().toString().toDouble().div(100.0).pow(2))
                            //bmi = sqrt(dataSnapshot.child("height").getValue().toString().toDouble().div(100.0))
                            if(bmi < 18.5) content.setText("BMI : "+bmi.toString() + "\n저체중입니다.")
                            else if((bmi >= 18.5) and (bmi < 23)) content.setText("BMI : "+bmi.toString() + "\n정상 체중입니다.")
                            else if((bmi >= 23) and (bmi < 25)) content.setText("BMI : "+bmi.toString() + "\n과체중입니다.")
                            else if(bmi >= 25) content.setText("BMI : "+bmi.toString() + "\n비만입니다.")
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

    private fun initUI(rootView:ViewGroup, xAxisValues:ArrayList<String>){
        chart = rootView.findViewById(R.id.linechart)
        chart!!.invalidate() //차트 초기화 작업
        chart!!.clear()
        chart!!.description.isEnabled = false
        chart!!.setDrawGridBackground(false)
        chart!!.setBackgroundColor(Color.WHITE)
        //chart!!.setViewPortOffsets(0f, 0f, 0f, 0f)

        val legend = chart!!.legend
        legend.isEnabled = false

        val xAxis = chart!!.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 10f
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.textColor = Color.BLACK
        xAxis.setCenterAxisLabels(true)
        xAxis.granularity = 21f
        //xAxis.setLabelCount(6, true)
        //xAxis.setValueFormatter(IndexAxisValueFormatter(xAxisValues))
        var str = ""
        xAxis.valueFormatter = object : ValueFormatter() {
           /* private val mFormat =
                SimpleDateFormat("MM-DD", Locale.KOREA)*/

            /*override fun getFormattedValue(value: Float): String? {
                val millis =
                    TimeUnit.HOURS.toMillis(value.toLong())
                return mFormat.format(Date(millis))
            }*/
            override fun getFormattedValue(value: Float): String? {
                /*val millis =
                    TimeUnit.HOURS.toMillis(xAxisValuesvalue.toLong())
                if(str < 5) return xAxisValues[str]
                else return value.toString()*/

                var temp = 0
                if(value == 42.0f) temp = 1
                else if(value == 63.0f) temp= 2
                else if(value == 84.0f) temp = 3
                else if(value == 105.0f) temp = 4
                else if(value == 21.0f) temp = 0

                return xAxisValues.get(temp)
            }
        }

        for(i in xAxisValues){

        }



        //xAxis.valueFormatter.getFormattedValue(20f)



        val leftAxis = chart!!.getAxisLeft()
        //leftAxis.isEnabled = false
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true
        leftAxis.axisMinimum = 40f
        leftAxis.axisMaximum = 70f
        leftAxis.yOffset = -9f

        val rightAxis= chart!!.getAxisRight()
        rightAxis.isEnabled = false

    }

    private fun setData(values : ArrayList<Entry>){
        val set1 = LineDataSet(values, "Weight")
        set1.axisDependency = YAxis.AxisDependency.LEFT
        set1.color = Color.rgb(97,93,188)
        set1.valueTextColor = Color.rgb(97,93,188)
        set1.lineWidth = 1.5f
        set1.setDrawCircles(true)
        set1.setDrawValues(true)
        set1.fillAlpha = 65
        set1.fillColor = Color.rgb(97,93,188)
        context?.let { ContextCompat.getColor(it, R.color.purple_200) }?.let {
            set1.setCircleColor(
                it
            )
        }
        set1.highLightColor = Color.rgb(244, 117, 117)
        set1.setDrawCircleHole(false)

        // create a data object with the data sets
        val data = LineData(set1)
        data.setValueTextColor(Color.BLACK)
        data.setValueTextSize(12f)

        // set data
        chart!!.data = data
        chart!!.invalidate()
    }

}