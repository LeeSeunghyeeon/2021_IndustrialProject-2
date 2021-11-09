package com.example.welt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.welt.databinding.ActivitySingInBinding
import com.example.welt.databinding.ActivitySingUp2Binding

class SingInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.BtnSignUp.setOnClickListener {
            val intent = Intent(this, SingUpActivity2::class.java)
            startActivity(intent)
        }
        binding.BtnSignIn.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        binding.BtnFindingID.setOnClickListener({
            val intent = Intent(this, Sign_Finding_ID::class.java)
            startActivity(intent)
        })
        binding.BtnFindingPW.setOnClickListener({
            val intent = Intent(this, Sign_Finding_PW::class.java)
            startActivity(intent)
        })

    }
}