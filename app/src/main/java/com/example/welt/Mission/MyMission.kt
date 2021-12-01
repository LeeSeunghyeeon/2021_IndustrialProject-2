package com.example.welt.Mission

import java.io.Serializable

class MyMission(var text:String, check:Boolean):Serializable {
    private var isSelected = check
    private var content = text

    fun getSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

    fun getContent():String {
        return content
    }

    fun setContent(text: String){
        content = text
    }

}