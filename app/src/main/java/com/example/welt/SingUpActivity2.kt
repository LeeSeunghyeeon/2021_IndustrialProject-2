package com.example.welt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.welt.databinding.ActivityMainBinding
import com.example.welt.databinding.ActivitySingUp2Binding

class SingUpActivity2 : AppCompatActivity() {
    lateinit var binding: ActivitySingUp2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
       binding.button7.setOnClickListener({
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
       })
    }
}