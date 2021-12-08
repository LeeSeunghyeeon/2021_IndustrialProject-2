package com.example.welt.Health

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.welt.R
import com.example.welt.Sign.database
import com.example.welt.databinding.FragmentHealthMealBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_health_meal.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.time.LocalDate
import javax.xml.parsers.DocumentBuilderFactory


var text = ""

class Health_meal : DialogFragment() {
    private lateinit var binding: FragmentHealthMealBinding
    val myRef = database.getReference("User")

    // 현재 user 가져오기
    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid // 현재 로그인한 사용자의 파이어베이스 uid

    lateinit var food_name:String
    lateinit var adapter: ArrayAdapter<String>


    // 현재 날짜 받아오기
    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    @SuppressLint("ResourceType", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthMealBinding.inflate(inflater, container, false)
        val item = resources.getStringArray(R.array.food)

        adapter = ArrayAdapter(getActivity()!!, android.R.layout.simple_dropdown_item_1line, item)
        binding.inputBreakfast.setAdapter(adapter)
        binding.inputLaunch.setAdapter(adapter)
        binding.inputDinner.setAdapter(adapter)


        try {
            myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val meal1 = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("breakfast").getValue()
                    val meal2 = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("launch").getValue()
                    val meal3 = snapshot.child(userID.toString()).child("Health").child(date.toString()).child("meal").child("dinner").getValue()
                    if (meal1 == null) {
                        binding.inputBreakfast.setHint("아침 메뉴를 입력하세요.")
                    } else {
                        binding.inputBreakfast.setText(meal1.toString())
                    }
                    if (meal2 == null) {
                        binding.inputLaunch.setHint("점심 메뉴를 입력하세요.")
                    } else {
                        binding.inputLaunch.setText(meal2.toString())
                    }
                    if (meal3 == null) {
                        binding.inputDinner.setHint("저녁 메뉴를 입력하세요.")
                    } else {
                        binding.inputDinner.setText(meal3.toString())
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", "에러")
                }
            })

        }catch (E:Exception) {


        }

        // 아침 메뉴 저장
        binding.breakfastOK.setOnClickListener {
            var food_name = input_breakfast.text.toString()
            val url : String =
                //"http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1?serviceKey=%2FdCv1qGNZQ6TMYaa8XsN5STAIC7Jw0wqqen41%2F7vcbXfWsT2%2BCokJ%2BpZYAw8puo7AqOQWWcI5Ws9AJ6qUazw%2BA%3D%3D&desc_kor=" + food_name + "&type=xml"
                "http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1?serviceKey=%2FdCv1qGNZQ6TMYaa8XsN5STAIC7Jw0wqqen41%2F7vcbXfWsT2%2BCokJ%2BpZYAw8puo7AqOQWWcI5Ws9AJ6qUazw%2BA%3D%3D&pageNo="+2222+"&type=xml"

            val thread = Thread(NetworkThread(url))
            thread.start()
            thread.join()

            binding.inputBreakfast.setText(text)

            try {
                var breakfast = input_breakfast.text.toString()
                insertDB("breakfast", breakfast)
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        // 아침 메뉴 삭제
        binding.breakfastDelete.setOnClickListener {
            deleteDB("breakfast")
            binding.inputBreakfast.setText("")

        }

        // 점심 메뉴 저장
        binding.launchOK.setOnClickListener {
            try {
                var launch = input_launch.text.toString()
                insertDB("launch", launch)
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        // 점심 메뉴 삭제
        binding.launchDelete.setOnClickListener {
            deleteDB("launch")
            binding.inputLaunch.setText("")
        }

        // 저녁 메뉴 저장
        binding.dinnerOK.setOnClickListener {
            try {
                var dinner = input_dinner.text.toString()
                insertDB("dinner", dinner)
            } catch (E:Exception) {
                Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 저녁 메뉴 삭제
        binding.dinnerDelete.setOnClickListener {
            deleteDB("dinner")
            binding.inputDinner.setText("")
        }

        // 확인
        binding.BtnMealOK.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun insertDB(timeOfMeal: String, setMenu: String) {
        if (setMenu.length > 0) {
            // 저장
            myRef.child(userID.toString()).child("Health").child(date.toString()).child("meal").child(timeOfMeal).setValue(setMenu)
        } else {
            // 저장 X
            Toast.makeText(getActivity(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteDB(timeOfMeal: String) {
        try {
            myRef.child(userID.toString()).child("Health").child(date.toString()).child("meal").child(timeOfMeal).setValue(null)
        } catch (E:Exception) {
        }
    }

    class NetworkThread(
        var url: String) : Runnable {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun run() {
            try {

                val xml: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)


                xml.documentElement.normalize()

                //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
                val list: NodeList = xml.getElementsByTagName("item")

                for (i in 0..list.length - 1) {
                //list.length-1 만큼 얻고자 하는 태그의 정보를 가져온다

                    val n: Node = list.item(1)

                    if (n.getNodeType() == Node.ELEMENT_NODE) {

                        val elem = n as Element

                        val map = mutableMapOf<String, String>()


                        for (j in 0..elem.attributes.length - 1) {

                            map.putIfAbsent(
                                elem.attributes.item(j).nodeName,
                                elem.attributes.item(j).nodeValue
                            )

                        }

                        println("=========${i + 1}=========")
                        text += "${i + 1}번 음식 \n"


                        println(
                            "1. 음식명 : ${
                                elem.getElementsByTagName("DESC_KOR").item(0).textContent
                            }"
                        )

                        text += "1. 음식명 : ${
                            elem.getElementsByTagName("DESC_KOR").item(0).textContent
                        } \n"


                        println(
                            "2. 열량 : ${
                                elem.getElementsByTagName("NUTR_CONT1").item(0).textContent
                            }"
                        )
                        text += "2. 열량 : ${
                            elem.getElementsByTagName("NUTR_CONT1").item(0).textContent
                        } \n"

                        println(
                            "3. 탄수화물: ${
                                elem.getElementsByTagName("NUTR_CONT2").item(0).textContent
                            }"
                        )
                        text += "3. 탄수화물 : ${
                            elem.getElementsByTagName("NUTR_CONT2").item(0).textContent
                        } \n"

                        println(
                            "4. 단백질 : ${
                                elem.getElementsByTagName("NUTR_CONT3").item(0).textContent
                            }"
                        )
                        text += "4. 단백질 : ${
                            elem.getElementsByTagName("NUTR_CONT3").item(0).textContent
                        } \n"

                        println(
                            "5. 지방 : ${
                                elem.getElementsByTagName("NUTR_CONT4").item(0).textContent
                            }"
                        )
                        text += "5. 지방 : ${
                            elem.getElementsByTagName("NUTR_CONT4").item(0).textContent
                        } \n"

                        println(
                            "6. 당류 : ${
                                elem.getElementsByTagName("NUTR_CONT5").item(0).textContent
                            }"
                        )
                        text += "6. 당류 : ${
                            elem.getElementsByTagName("NUTR_CONT5").item(0).textContent
                        } \n"

                    }
                    }

            } catch (e: Exception) {
                Log.d("TTT", "오픈API" + e.toString())
            }
        }
    }


}


