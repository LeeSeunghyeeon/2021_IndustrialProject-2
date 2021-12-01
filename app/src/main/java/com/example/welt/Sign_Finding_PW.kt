package com.example.welt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.welt.databinding.ActivitySignFindingIdBinding
import com.example.welt.databinding.ActivitySignFindingPwBinding
import com.example.welt.databinding.ActivitySingUp2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_finding_id.*
import java.io.IOException

var mAuth: FirebaseAuth? =null
class Sign_Finding_PW : AppCompatActivity() {
    lateinit var binding: ActivitySignFindingPwBinding
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFindingPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.BtnFindingPWNext.setOnClickListener{

            if(user_name.length()>0&&user_phone.length()>0){
                var name = binding.userName.text.toString()
                var phone = binding.userPhone.text.toString()
                var id = binding.userId.text.toString()

                databaseRef = FirebaseDatabase.getInstance().getReference("User")
                databaseRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var state:Boolean=false
                        for (messageData in dataSnapshot.children) {
                            val user_id = messageData.child("UserInfo").child("user_id").getValue().toString()
                            val user_name = messageData.child("UserInfo").child("user_name").getValue().toString()
                            val user_phone = messageData.child("UserInfo").child("user_phone").getValue().toString()
                            if (name == user_name && phone == user_phone &&id==user_id) {
                                FirebaseAuth.getInstance().sendPasswordResetEmail(user_id.toString())
                                    .addOnCompleteListener {	task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this@Sign_Finding_PW, "비밀번호 재설정 메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(this@Sign_Finding_PW, "정확한 정보를 입력하세요.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                state=true
                            }
                        }
                        if(state==false){
                            Toast.makeText(this@Sign_Finding_PW, "정확한 정보를 입력하세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

            }else{
                Toast.makeText(this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

        }
        binding.BtnPWCancel.setOnClickListener {
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
        }

    }
}