package com.example.welt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.welt.Content.ContentFragment
import com.example.welt.Health.HealthFragment
import com.example.welt.Mission.MissionFragment

class MainFragmentStatePagerAdapter(fm : FragmentManager, val fragmentCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return ContentFragment()
            1 -> return MissionFragment()
            2 -> return HealthFragment()
            3 -> return DiaryFragment()
            else -> return ContentFragment()
        }
    }

    override fun getCount(): Int = fragmentCount // 자바에서는 { return fragmentCount }

}