package com.example.welt.Mission

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import java.time.LocalDate

class Mission_eatCalDialog : DialogFragment(), View.OnClickListener {
    private lateinit var btnOK: Button

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
            inflater.inflate(R.layout.fragment_mission_eat_cal_dialog, container, false) as ViewGroup
////////
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        btnOK = rootView.findViewById(R.id.btn_OK)
        btnOK.setOnClickListener {
            dismiss()
        }

        return rootView
    }

    override fun onClick(v: View?) {
        dismiss()
    }

}