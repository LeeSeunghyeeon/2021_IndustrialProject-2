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
import com.example.welt.databinding.ActivitySingInBinding
import com.example.welt.databinding.ActivitySingUp2Binding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sing_up2.*
import kotlin.reflect.KClass

class SingUpActivity2 : AppCompatActivity() {
    private var mAuth: FirebaseAuth? =null
    lateinit var binding: ActivitySingUp2Binding

//    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val myRef : DatabaseReference = database.reference
    
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

//    private fun input_User_Info(){
//        var user = mAuth?.currentUser
//        var user_uid=user?.uid
//        if (user_uid != null) {
//            myRef.child("User").child(user_uid).setValue("hi")
//        }
//    }
    private fun signUp(){
        if(binding.inputName.length()>0&&binding.inputBabyName.length()>0&&binding.inputTelNum.length()>0&&binding.inputId.length()>0&&binding.inputPw.length()>0){
            var id = binding.inputId.text.toString()
            var pw = binding.inputPw.text.toString()
            mAuth?.createUserWithEmailAndPassword(id, pw)
                ?.addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        var user_uid=mAuth?.uid
                        var user_babyBirth_y=binding.babyDateSpinner.year
                        var user_babyBirth_m=binding.babyDateSpinner.month
                        var user_babyBirth_d=binding.babyDateSpinner.dayOfMonth
                        var user_baby_name=binding.inputBabyName.text.toString()
                        var user_birth_y=binding.parentDateSpinner.year
                        var user_birth_m=binding.parentDateSpinner.month
                        var user_birth_d=binding.parentDateSpinner.dayOfMonth
                        var user_name=binding.inputName.text.toString()
                        var user_phone=binding.inputTelNum.text.toString()
                        var user_id=binding.inputId.text.toString()
                        var user_pw=binding.inputPw.text.toString()
                        var user_relationship=binding.spinnerParent.selectedItem.toString()

                        var user_babyBirth:String
                        var user_birth:String
                        var zero:Int=0
                        if(user_relationship=="엄마👩"){
                            user_relationship="엄마"
                        }else if(user_relationship=="아빠👨"){
                            user_relationship="아빠"
                        }
                        if(user_babyBirth_m<10&&user_babyBirth_d<10){
                            user_babyBirth=user_babyBirth_y.toString()+zero.toString()+(user_babyBirth_m+1).toString()+zero.toString()+user_babyBirth_d.toString()
                        }else if(binding.babyDateSpinner.dayOfMonth<10){
                            user_babyBirth=user_babyBirth_y.toString()+(user_babyBirth_m+1).toString()+zero.toString()+user_babyBirth_d.toString()
                        }else if(binding.babyDateSpinner.month<10){
                            user_babyBirth=user_babyBirth_y.toString()+zero.toString()+(user_babyBirth_m+1)+user_babyBirth_d.toString()
                        }else{
                            user_babyBirth=user_babyBirth_y.toString()+(user_babyBirth_m+1).toString()+user_babyBirth_d.toString()
                        }

                        if(binding.parentDateSpinner.month<10&&binding.babyDateSpinner.dayOfMonth<10){
                            user_birth=user_birth_y.toString()+zero.toString()+(user_birth_m+1).toString()+zero.toString()+user_birth_d.toString()
                        }else if(binding.babyDateSpinner.dayOfMonth<10){
                            user_birth=user_birth_y.toString()+(user_birth_m+1).toString()+zero.toString()+user_birth_d.toString()
                        }else if(binding.parentDateSpinner.month<10){
                            user_birth=user_birth_y.toString()+zero.toString()+(user_birth_m+1).toString()+user_birth_d.toString()
                        }else{
                            user_birth=user_birth_y.toString()+(user_birth_m+1).toString()+user_birth_d.toString()
                        }

                        if (user_uid != null) {
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_babyBirth").setValue(user_babyBirth)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_baby_name").setValue(user_baby_name)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_birth").setValue(user_birth)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_id").setValue(user_id)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_name").setValue(user_name)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_phone").setValue(user_phone)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_pw").setValue(user_pw)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_relationship").setValue(user_relationship)
                        }
                        //val user = mAuth.currentUser
                        Toast.makeText(this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        changeActivity(SingInActivity::class.java)
                    } else {
                        if (task.exception != null) {
                            if(task.exception.toString()=="com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."){
                                Toast.makeText(this, "이미 있는 아이디입니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show()
                            }else if(task.exception.toString()=="com.google.firebase.auth.FirebaseAuthWeakPasswordException: The given password is invalid. [ Password should be at least 6 characters ]"){
                                Toast.makeText(this, "비밀번호는 최소 6자리 이상이어야 합니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        }else{
            Toast.makeText(this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}