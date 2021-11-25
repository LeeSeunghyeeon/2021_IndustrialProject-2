package com.example.welt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class Mission_Adapter(var item_list: ArrayList<MyMission>) :
    RecyclerView.Adapter<Mission_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.mission_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.chkSelected.text = item_list[position].getContent()
        holder.chkSelected.isChecked = item_list[position].getSelected()
        holder.chkSelected.tag = item_list[position]
        holder.chkSelected.setOnClickListener { v ->
            val cb = v as CheckBox
            val model = cb.tag as MyMission
            model.setSelected(cb.isChecked)
            item_list[position].setSelected(cb.isChecked)
        }

    }

    override fun getItemCount(): Int {
        if (item_list != null) {
            return item_list.size
        }
        return 1
    }

    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var chkSelected: CheckBox

        init {
            chkSelected = itemLayoutView.findViewById<View>(R.id.mission_check) as CheckBox
        }
    }
}