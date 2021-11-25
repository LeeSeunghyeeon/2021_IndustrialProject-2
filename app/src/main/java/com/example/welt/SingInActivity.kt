package com.example.welt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.welt.databinding.ActivitySingInBinding
import com.example.welt.databinding.ActivitySingUp2Binding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

val database : FirebaseDatabase = FirebaseDatabase.getInstance()
val myRef : DatabaseReference = database.reference

class SingInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingInBinding
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        init()
    }

    private fun init(){
        binding.BtnSignUp.setOnClickListener {
            changeActivity(SingUpActivity2::class.java)
        }
        binding.BtnSignIn.setOnClickListener({
<<<<<<< HEAD
=======
//            myRef.child("test").setValue("hi")
>>>>>>> 69f9630980142fa128a0ef7a6c29437cb7cd965f
            signIn()

        })
        binding.BtnFindingID.setOnClickListener({
            changeActivity(Sign_Finding_ID::class.java)
        })
        binding.BtnFindingPW.setOnClickListener({
            changeActivity(Sign_Finding_PW::class.java)
        })

    }

    private fun signIn(){
        if (binding.inputId.length() > 0 && binding.inputPW.length() > 0) {
            mAuth.signInWithEmailAndPassword(binding.inputId.text.toString(), binding.inputPW.text.toString())
                .addOnCompleteListener(this,
                    OnCompleteListener<AuthResult?> { task ->
                        if (task.isSuccessful) {
                            val user: FirebaseUser? = mAuth.getCurrentUser()
                            Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                            changeActivity(MainActivity::class.java)
                            binding.inputId.text=null
                            binding.inputPW.text=null
                        } else {
                            if (task.exception != null) {
                                Toast.makeText(this, "로그인 정보를 다시 확인하세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
        } else {
            Toast.makeText(this, "아이디 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}