package com.example.welt.Sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.welt.databinding.ActivitySignFindingIdBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Sign_Finding_ID : AppCompatActivity() {
    lateinit var binding: ActivitySignFindingIdBinding
    private var mAuth: FirebaseAuth? = null
    private lateinit var databaseRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFindingIdBinding.inflate(layoutInflater)

        mAuth = Firebase.auth

        setContentView(binding.root)

        init()
    }


    private fun init() {
        binding.BtnFindingIDNext.setOnClickListener {
            var name = binding.userName.text.toString()
            var phone = binding.userPhone.text.toString()


            if (name != "" && phone != "") {
                databaseRef = FirebaseDatabase.getInstance().getReference("User")
                databaseRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var state:Boolean=false
                        for (messageData in dataSnapshot.children) {
                            val user_id = messageData.child("UserInfo").child("user_id").getValue().toString()
                            val user_name = messageData.child("UserInfo").child("user_name").getValue().toString()
                            val user_phone = messageData.child("UserInfo").child("user_phone").getValue().toString()
                            if (name == user_name && phone == user_phone) {
                                binding.IDMessage.text = "아이디는 " + user_id + " 입니다."
                                binding.IDMessage.visibility = View.VISIBLE
                                state=true
                            }
                        }
                        if(state==false){
                            binding.IDMessage.text = "없는 정보 입니다."
                            binding.IDMessage.visibility = View.VISIBLE
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })
            } else {
                Toast.makeText(this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.BtnIDCancel.setOnClickListener {
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
        }

    }
}