package com.example.welt.Sign

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.welt.R
import com.example.welt.databinding.ActivitySingUp2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class SingUpActivity2 : AppCompatActivity() {
    private var mAuth: FirebaseAuth? =null
    lateinit var binding: ActivitySingUp2Binding

    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.now()

//    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val myRef : DatabaseReference = database.reference

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addMission(baby_birth : String) {
        var user_uid=mAuth?.uid
        val currentDate: LocalDateTime = LocalDateTime.now()
        val cal_today = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))


        val startDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(cal_today.toString())
        val endDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(baby_birth.toString())
        val remaining_days = (endDate.time - startDate.time) / (24 * 60 * 60 * 1000)
        myRef=database.getReference("User").child(user_uid.toString()).child("Mission")
        for(i in 0..remaining_days){
            myRef.child(date.plusDays(i).toString()).child("아침 식단 기록하기").setValue(false)
            myRef.child(date.plusDays(i).toString()).child("점심 식단 기록하기").setValue(false)
            myRef.child(date.plusDays(i).toString()).child("저녁 식단 기록하기").setValue(false)
            myRef.child(date.plusDays(i).toString()).child("수면 기록하기").setValue(false)
            myRef.child(date.plusDays(i).toString()).child("운동하기").setValue(false)

            if(i%14 == 0L){
                myRef.child(date.plusDays(i).toString()).child("체중 기록하기").setValue(false)
            }
            if(i%7 == 0L){
                myRef.child(date.plusDays(i).toString()).child("주차별 정보 학습하기").setValue(false)
            }

        }
    }

    //    private fun input_User_Info(){
//        var user = mAuth?.currentUser
//        var user_uid=user?.uid
//        if (user_uid != null) {
//            myRef.child("User").child(user_uid).setValue("hi")
//        }
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun signUp(){
        if(binding.inputName.length()>0&&binding.inputBabyName.length()>0&&binding.inputTelNum.length()>0&&binding.inputId.length()>0&&binding.inputPw.length()>0&&binding.inputHeight.length()>0&&binding.inputWeight.length()>0){
            var id = binding.inputId.text.toString()
            var pw = binding.inputPw.text.toString()
            mAuth?.createUserWithEmailAndPassword(id, pw)
                ?.addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        var user_uid=mAuth?.uid
                        var user_babyBirth_y=binding.babyDateSpinner.year
                        var user_babyBirth_m=binding.babyDateSpinner.month+1
                        var user_babyBirth_d=binding.babyDateSpinner.dayOfMonth
                        var user_baby_name=binding.inputBabyName.text.toString()
                        var user_birth_y=binding.parentDateSpinner.year
                        var user_birth_m=binding.parentDateSpinner.month+1
                        var user_birth_d=binding.parentDateSpinner.dayOfMonth
                        var user_name=binding.inputName.text.toString()
                        var user_phone=binding.inputTelNum.text.toString()
                        var user_id=binding.inputId.text.toString()
                        var user_pw=binding.inputPw.text.toString()
                        var user_relationship=binding.spinnerParent.selectedItem.toString()
                        var user_height=binding.inputHeight.text.toString()
                        var user_weight=binding.inputWeight.text.toString()

                        var num:Int=0

                        var user_babyBirth:String
                        var user_birth:String
                        var zero:Int=0
                        if(user_relationship=="엄마👩"){
                            user_relationship="엄마"
                        }else if(user_relationship=="아빠👨"){
                            user_relationship="아빠"
                        }
                        if(user_babyBirth_m<10&&user_babyBirth_d<10){
                            user_babyBirth=user_babyBirth_y.toString()+zero.toString()+(user_babyBirth_m).toString()+zero.toString()+user_babyBirth_d.toString()
                        }else if(user_babyBirth_m>10&&user_babyBirth_d<10){
                            user_babyBirth=user_babyBirth_y.toString()+(user_babyBirth_m).toString()+zero.toString()+user_babyBirth_d.toString()
                        }else if(user_babyBirth_d>10&&user_babyBirth_m<10){
                            user_babyBirth=user_babyBirth_y.toString()+zero.toString()+(user_babyBirth_m)+user_babyBirth_d.toString()
                        }else{
                            user_babyBirth=user_babyBirth_y.toString()+(user_babyBirth_m).toString()+user_babyBirth_d.toString()
                        }

                        if(user_birth_m<10&&user_birth_d<10){
                            user_birth=user_birth_y.toString()+zero.toString()+(user_birth_m).toString()+zero.toString()+user_birth_d.toString()
                        }else if(user_birth_m>10&&user_birth_d<10){
                            user_birth=user_birth_y.toString()+(user_birth_m).toString()+zero.toString()+user_birth_d.toString()
                        }else if(user_birth_d>10&&user_birth_m<10){
                            user_birth=user_birth_y.toString()+zero.toString()+(user_birth_m).toString()+user_birth_d.toString()
                        }else{
                            user_birth=user_birth_y.toString()+(user_birth_m).toString()+user_birth_d.toString()
                        }



                        val now: Long = System.currentTimeMillis()
                        val mDate = Date(now)
                        val simpleDate = SimpleDateFormat("yyyyMMdd")
                        val getTime: String = simpleDate.format(mDate)

                        if (user_uid != null) {
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_babyBirth").setValue(user_babyBirth)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_baby_name").setValue(user_baby_name)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_birth").setValue(user_birth)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_id").setValue(user_id)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_name").setValue(user_name)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_phone").setValue(user_phone)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_pw").setValue(user_pw)
                            myRef.child("User").child(user_uid).child("UserInfo").child("user_relationship").setValue(user_relationship)
                            myRef.child("User").child(user_uid).child("Health").child(getTime).child("height").setValue(user_height)
                            myRef.child("User").child(user_uid).child("Health").child(getTime).child("weight").setValue(user_weight)

                        }
                        addMission(user_babyBirth)
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