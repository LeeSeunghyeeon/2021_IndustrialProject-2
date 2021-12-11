package com.example.welt.Content

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.welt.Health.Health_Adapter
import com.example.welt.Mission.MyMission
import com.example.welt.Sign.database
import com.example.welt.Sign.myRef
import com.example.welt.databinding.FragmentContentHospitalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*



class Content_Hospital : Fragment() {
    private var data:ArrayList<MyMission> = ArrayList()
    private var data2:ArrayList<MyMission> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerView2: RecyclerView
    lateinit var adapter: Health_Adapter
    lateinit var adapter2: Health_Adapter
    private lateinit var binding: FragmentContentHospitalBinding

    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var time = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ISO_TIME
    @RequiresApi(Build.VERSION_CODES.O)
    val formatted = time.format(formatter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showItemList()
    }

    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentHospitalBinding.inflate(inflater, container, false)
        initData()

        binding.HospitalOKbtn.setOnClickListener{
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().remove(Content_Hospital()).commit()
                fragmentManager.popBackStack()
            }
        }
        binding.hospitalAddBtn.setOnClickListener{
            val addHospitalScheduleDialog = Content_HospitalAdd()
            activity?.supportFragmentManager?.let { fragmentManager ->
                addHospitalScheduleDialog.show(fragmentManager, "Content_addHospitalDialog")
            }
        }

        binding.hospitalRemoveBtn.setOnClickListener{
            myRef = database.getReference("User")
            // 취소 시 실행할 코드
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            for(i in 0 until data.size){
                if(adapter.item_list[i].getSelected()){
                    val year= adapter.item_list[i].text.split("년 ")
                    val month = year[1].split("월 ")
                    val da = month[1].split("일 ")
                    val ti = da[2]
                    myRef.child(uid.toString()).child("HospitalSchedule").child(year[0] + "-" + month[0] + "-" + da[0] + " "+ ti.substring(0,5)).setValue(null)
                    adapter.notifyDataSetChanged()
                }
            }
            for(i in 0 until data2.size){
                if(adapter2.item_list[i].getSelected()){
                    val year= adapter2.item_list[i].text.split("년 ")
                    val month = year[1].split("월 ")
                    val da = month[1].split("일 ")
                    val ti = da[2]
                    myRef.child(uid.toString()).child("HospitalSchedule").child(year[0] + "-" + month[0] + "-" + da[0] + " "+ ti.substring(0,5)).setValue(null)
                    adapter2.notifyDataSetChanged()
                }
            }
            Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
    // 무슨요일인지 계산
    fun calDayOfWeek(inputDate:String) :String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
        val date: Date = dateFormat.parse(inputDate)
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTime(date)
        var day = calendar.get(Calendar.DAY_OF_WEEK)
        if (day == 1)
            return "일요일"
        else if (day==2)
            return "월요일"
        else if (day==3)
            return "화요일"
        else if (day==4)
            return "수요일"
        else if (day==5)
            return "목요일"
        else if (day==6)
            return "금요일"
        else if (day==7)
            return "토요일"
        else
            return ""
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView1
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = Health_Adapter(data)

        recyclerView2 = binding.recyclerView2
        recyclerView2.setHasFixedSize(true)

        recyclerView2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter2 = Health_Adapter(data2)
    }

    private fun initData() {
        data.clear()
        data2.clear()
        if (uid != null) {
            myRef = FirebaseDatabase.getInstance().getReference("User").child(uid.toString()).child("HospitalSchedule")
            myRef
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        data.clear()
                        data2.clear()
                        for (messageData in snapshot.children) {
                            var target_dayOfWeek = calDayOfWeek("%s%s%s".format(messageData.key.toString().substring(0,4),messageData.key.toString().substring(5,7),messageData.key.toString().substring(8,10)))
                            var target_viewText = "%s년 %s월 %s일 %s %s".format(messageData.key.toString().substring(0,4),messageData.key.toString().substring(5,7),messageData.key.toString().substring(8,10),target_dayOfWeek,messageData.key.toString().substring(11,16))
                            var text = ""
                            text += target_viewText + " | "
                            var target_memo = messageData.getValue().toString()
                            text += target_memo
                            val cc = false

                            if(messageData.key.toString().compareTo(date.toString()+" "+ formatted.toString()) <= 0){
                                data.add(MyMission(text, cc))
                            }else{
                                data2.add(MyMission(text, cc))
                            }

                        }
                        adapter.notifyDataSetChanged()
                        adapter2.notifyDataSetChanged()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("Failed")
                    }
                })
        }

        initRecyclerView()
    }

    fun showItemList() {
        adapter = Health_Adapter(data)
        recyclerView.setAdapter(adapter)

        adapter2 = Health_Adapter(data2)
        recyclerView2.setAdapter(adapter2)
    }
}