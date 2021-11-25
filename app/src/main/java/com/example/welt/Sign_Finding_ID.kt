package com.example.welt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.view.OneShotPreDrawListener
import com.example.welt.databinding.ActivitySignFindingIdBinding
import com.example.welt.databinding.ActivitySingInBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference

import com.google.firebase.auth.FirebaseAuth






class Sign_Finding_ID : AppCompatActivity() {
    lateinit var binding: ActivitySignFindingIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFindingIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }


    private fun init(){
        binding.BtnFindingIDNext.setOnClickListener{
            var name=binding.userName.text.toString()
            var phone=binding.userPhone.text.toString()

            var user_name:String=""
            var user_phone:String=""
            var user_id:String=""

            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid

            if(name!=""&&phone!=""){
                myRef.child("User").child(uid.toString()).child("UserInfo").child("user_id").get().addOnSuccessListener {
                    user_id=it.value.toString()
                    Log.i("firebase", "Got value ${it.value}")

                    myRef.child("User").child(uid.toString()).child("UserInfo").child("user_name").get().addOnSuccessListener {
                        user_name= it.value.toString()
                        Log.i("firebase", "Got value ${it.value}")

                        myRef.child("User").child(uid.toString()).child("UserInfo").child("user_phone").get().addOnSuccessListener {
                            user_phone= it.value.toString()
                            Log.i("firebase", "Got value ${it.value}")

                            if(name == user_name && phone == user_phone){
                                binding.IDMessage.text= "아이디는 "+ user_id +" 입니다."
                                binding.IDMessage.visibility= View.VISIBLE
                            }else {
                                Toast.makeText(this, "잘못된 정보입니다.", Toast.LENGTH_SHORT).show()
                            }

                        }.addOnFailureListener{
                            Log.e("firebase", "Error getting data", it)
                        }

                    }.addOnFailureListener{
                        Log.e("firebase", "Error getting data", it)
                    }

                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }

            }else{
                Toast.makeText(this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.BtnIDCancel.setOnClickListener {
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
        }

    }
}