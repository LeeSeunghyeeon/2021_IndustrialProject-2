package com.example.welt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.welt.databinding.ActivitySignFindingIdBinding
import com.example.welt.databinding.ActivitySignFindingPwBinding

class Sign_Finding_PW : AppCompatActivity() {
    lateinit var binding: ActivitySignFindingPwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFindingPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.BtnPWCancel.setOnClickListener {
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
        }

    }
}