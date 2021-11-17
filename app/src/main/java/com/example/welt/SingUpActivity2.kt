package com.example.welt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.welt.databinding.ActivityMainBinding
import com.example.welt.databinding.ActivitySingUp2Binding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.reflect.KClass

class SingUpActivity2 : AppCompatActivity() {
    private var mAuth: FirebaseAuth? =null
    lateinit var binding: ActivitySingUp2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth

        val spinner: Spinner = binding.spinnerParent
        ArrayAdapter.createFromResource(
            this, R.array.parent_arr, android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }


        binding.BtnSignUp.setOnClickListener({
            signUp()
        })


    }

    private fun signUp(){
        var id = binding.inputId.text.toString()
        var pw = binding.inputPw.text.toString()
        mAuth?.createUserWithEmailAndPassword(id, pw)
            ?.addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    //val user = mAuth.currentUser
                    Toast.makeText(this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    changeActivity(SingInActivity::class.java)
                } else {
                    if (task.exception != null) {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }

    private fun changeActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}