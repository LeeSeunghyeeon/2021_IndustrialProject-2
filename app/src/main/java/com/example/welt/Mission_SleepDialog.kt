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
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.fragment_mission__sleep_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.github.mikephil.charting.data.ChartData as ChartData1

class Mission_SleepDialog : DialogFragment(), View.OnClickListener {
    var chart: PieChart? = null
    private lateinit var btnOK: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView =
            inflater.inflate(R.layout.fragment_mission__sleep_dialog, container, false) as ViewGroup
////////
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        btnOK = rootView.findViewById(R.id.btn_OK)
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
        val entries = ArrayList<PieEntry>()
        entries.add(
            PieEntry(
                50.0f, "",
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_bedtime_24, null)
            )

        )
        entries.add(
            PieEntry(
                50.0f, "",
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_wb_sunny_24, null)
            )

        )

        val dataSet = PieDataSet(entries, "수면 시간")
        dataSet.setDrawIcons(true)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, (-40).toFloat())
        dataSet.selectionShift = 5f
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.JOYFUL_COLORS) {
            colors.add(c)
        }
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueTextSize(22.0f)
        data.setValueTextColor(Color.WHITE)
        chart!!.data = data
        chart!!.invalidate()
    }


}