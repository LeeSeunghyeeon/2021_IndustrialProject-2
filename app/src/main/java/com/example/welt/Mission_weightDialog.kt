package com.example.welt

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.github.mikephil.charting.data.ChartData as ChartData1

class Mission_weightDialog : DialogFragment(), View.OnClickListener {
    var chart :LineChart?= null
    private lateinit var btnOK:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_mission_weight_dialog, container, false) as ViewGroup

        btnOK = rootView.findViewById(R.id.btn_OK)
        btnOK.setOnClickListener{
            dismiss()
        }

        initUI(rootView)
        return rootView
    }

    override fun onClick(v: View?) {
        dismiss()
    }

    private fun initUI(rootView:ViewGroup){
        chart = rootView.findViewById(R.id.linechart)
        chart!!.description.isEnabled = false
        chart!!.setDrawGridBackground(false)
        chart!!.setBackgroundColor(Color.WHITE)
        chart!!.setViewPortOffsets(0f, 0f, 0f, 0f)

        val legend = chart!!.legend
        legend.isEnabled = false

        val xAxis = chart!!.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.textSize = 10f
        xAxis.textColor = Color.WHITE
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(true)
        xAxis.textColor = Color.BLACK
        xAxis.setCenterAxisLabels(true)
        xAxis.granularity = 1f
        xAxis.setValueFormatter(object : ValueFormatter() {
            private val mFormat =
                SimpleDateFormat("MM-DD", Locale.KOREA)

            override fun getFormattedValue(value: Float): String? {
                val millis =
                    TimeUnit.HOURS.toMillis(value.toLong())
                return mFormat.format(Date(millis))
            }
        })

        val leftAxis = chart!!.getAxisLeft()
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true
        leftAxis.axisMinimum = 30f
        leftAxis.axisMaximum = 100f
        leftAxis.yOffset = -9f
        leftAxis.textColor = Color.BLACK

        val rightAxis= chart!!.getAxisRight()
        rightAxis.isEnabled = false

        setData()
    }

    private fun setData(){
        val values =
            ArrayList<Entry>()
        values.add(Entry(24f, 53.0f))
        values.add(Entry(48f, 54.0f))
        values.add(Entry(72f, 54.5f))
        values.add(Entry(96f, 55.0f))
        values.add(Entry(120f, 56.0f))

        // create a dataset and give it a type
        val set1 = LineDataSet(values, "DataSet 1")
        set1.axisDependency = YAxis.AxisDependency.LEFT
        set1.color = Color.rgb(97,93,188)
        set1.valueTextColor = Color.rgb(97,93,188)
        set1.lineWidth = 1.5f
        set1.setDrawCircles(true)
        set1.setDrawValues(false)
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
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(9f)

        // set data
        chart!!.data = data
        chart!!.invalidate()
    }

}