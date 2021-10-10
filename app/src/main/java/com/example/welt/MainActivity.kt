package com.example.welt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.welt.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
/*

    private val ContentFragment by lazy { ContentFragment() }
    private val MissionFragment by lazy { MissionFragment() }
    private val HealthFragment by lazy { HealthFragment() }
    private val DiaryFragment by lazy { DiaryFragment() }

*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureBottomNavigation()
    }

    private fun configureBottomNavigation(){
        binding.vpAcMainFragPager.adapter = MainFragmentStatePagerAdapter(supportFragmentManager, 4)

        binding.tlAcMainBottomMenu.setupWithViewPager(binding.vpAcMainFragPager)

        val bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)

        binding.tlAcMainBottomMenu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_home_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_mission_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_health_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_diary_tab) as RelativeLayout

    }

    fun init(){
        //var vp_ac_main_frag_pager = findViewById<ViewPager>(R.id.vp_ac_main_frag_pager)
        //var tl_ac_main_bottom_menu =findViewById<TabLayout>(R.id.tl_ac_main_bottom_menu)

        /*supportFragmentManager.beginTransaction().replace(R.id.container, HealthFragment).commit();
        binding.bnvMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.content -> {
                    //changeFragment(ContentFragment)
                    supportFragmentManager.beginTransaction().replace(R.id.container, ContentFragment).commit();
                }
                R.id.mission -> {
                    changeFragment(MissionFragment)
                }
                R.id.health -> {
                    changeFragment(HealthFragment)
                }
                R.id.diary -> {
                    changeFragment(DiaryFragment)

                    //return@OnNavigationItemSelectedListener true
                }
                else -> false
            }
            true
        }*/
    }
/*

    private val mOnNavigationiItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when(item.itemId){
            R.id.content -> {
                changeFragment(ContentFragment)
                return@OnItemSelectedListener true
            }
            R.id.mission -> {
                changeFragment(MissionFragment)
                return@OnItemSelectedListener true
            }
            R.id.health -> {
                changeFragment(HealthFragment)
                return@OnItemSelectedListener true
            }
            R.id.diary -> {
                changeFragment(DiaryFragment)
                return@OnItemSelectedListener true

                //return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    private fun changeFragment(fragment: Fragment){
        val fragmemtTransaction = supportFragmentManager.beginTransaction()
        fragmemtTransaction.replace(R.id.container, HealthFragment)
        fragmemtTransaction.commit()
    }
*/

}


