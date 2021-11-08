package com.example.welt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.welt.databinding.ActivitySignFindingIdBinding
import com.example.welt.databinding.ActivitySingInBinding

class Sign_Finding_ID : AppCompatActivity() {
    lateinit var binding: ActivitySignFindingIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFindingIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.BtnIDCancel.setOnClickListener {
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
        }

    }
}