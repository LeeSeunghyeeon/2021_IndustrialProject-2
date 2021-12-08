package com.example.welt

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.welt.databinding.FragmentDiaryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_diary.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*



class DiaryFragment : Fragment() {
    private lateinit var byteArray: ByteArray
    private lateinit var binding: FragmentDiaryBinding
    
    lateinit var fname: String
    lateinit var str: String
    lateinit var simage : String
    private val TAG = "FirebaseService"
//    private val OPEN_GALLERY = 1

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    val user = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid

    private var uri: Uri? = null
    private val Gallery = 1

    val currentDate = currentDate()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentDiaryBinding.inflate(inflater, container, false)


        binding.today.text = currentDate.dateToString("yyyy년 MM월 dd일")


        myRef = myRef.child(userID.toString()).child("UserInfo")


        Dday_current()
        test()

        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun Dday_current() {
        if (userID != null) {
            myRef= FirebaseDatabase.getInstance().getReference("User").child(userID.toString()).child("UserInfo")

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val baby_birth = snapshot.child("user_babyBirth").getValue() //출산예정일
                    val currentDate: LocalDateTime = LocalDateTime.now() //오늘 날짜
                    val cal_today = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale("ko", "KR")))
                    val startDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(cal_today.toString())
                    val endDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(baby_birth.toString())
                    val remaining_days = (endDate.time - startDate.time) / (24 * 60 * 60 * 1000)

                    binding.calDday.setText("아이와 만나기까지 D-%s".format(remaining_days))
                    binding.ExpectedDate.setText("출산 예정일 : %s년 %s월 %s일".format(baby_birth.toString().substring(0,4),baby_birth.toString().substring(4,6),baby_birth.toString().substring(6,8)))
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed")
                }
            })
        }
    }

    private fun Date.dateToString(format: String, local: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, local)
        return formatter.format(this)
    }


    private fun currentDate(): Date {
        return Calendar.getInstance().time
    }


    //날짜 클릭 및 저장 버튼
    private fun test() {
        binding.calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            binding.today.text = String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth)


            binding.today.visibility = View.VISIBLE
            binding.contextEditText.visibility = View.VISIBLE
            binding.saveBtn.visibility = View.VISIBLE
            binding.diaryContent.visibility = View.INVISIBLE
            binding.chaBtn.visibility = View.INVISIBLE
            binding.delBtn.visibility = View.INVISIBLE


            if (userID != null) {
                checkDay(year, month, dayOfMonth, userID)
            }


            contextEditText.setText("")


            binding.saveBtn.setOnClickListener {

                saveDiary(fname)

                str = contextEditText.getText().toString()

                myRef.child("User").child(userID.toString()).child("Diary")
                    .child(String.format("%d-%d-%d ", year, month + 1, dayOfMonth)).child("Diary_Text").setValue(str)
                Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()

                diaryContent.text = "${str}"

                binding.contextEditText.visibility = View.INVISIBLE
                binding.saveBtn.visibility = View.INVISIBLE
                binding.chaBtn.visibility = View.VISIBLE
                binding.delBtn.visibility = View.VISIBLE
                binding.diaryContent.visibility = View.VISIBLE


            }

            binding.chaBtn.setOnClickListener { // 수정 버튼을 누를 시
                binding.contextEditText.visibility = View.VISIBLE
                binding.diaryContent.visibility = View.INVISIBLE
                binding.contextEditText.setText(str) // editText에 textView에 저장된 내용을 출력
                binding.saveBtn.visibility = View.VISIBLE
                binding.chaBtn.visibility = View.INVISIBLE
                binding.delBtn.visibility = View.INVISIBLE
                binding.diaryContent.text = "${contextEditText.getText()}"

                myRef.child("User").child(userID.toString()).child("Diary")
                    .child(String.format("%d-%d-%d ", year, month + 1, dayOfMonth)).setValue(str)
            }

            binding.delBtn.setOnClickListener {
                binding.diaryContent.visibility = View.INVISIBLE
                binding.contextEditText.setText("")
                binding.contextEditText.visibility = View.VISIBLE
                binding.saveBtn.visibility = View.VISIBLE
                binding.chaBtn.visibility = View.INVISIBLE
                binding.delBtn.visibility = View.INVISIBLE

                removeDiary(fname)
                myRef.child("User").child(userID.toString()).child("Diary")
                    .child(String.format("%d-%d-%d ", year, month + 1, dayOfMonth)).setValue(null)

                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }


        })


    }


    private fun checkDay(cYear: Int, cMonth: Int, cDay: Int, userID: String) {

        fname = "" + userID + " " + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"
        //저장할 파일 이름 : ex) fN2FASdnfDA3 2021-11-22.txt
        var fis: FileInputStream? = null

        try {
            fis = activity?.openFileInput(fname)

            var fileData = fis?.let { ByteArray(it.available()) }

            fis?.read(fileData)
            fis?.close()

            str = String(fileData!!)

            contextEditText.visibility = View.INVISIBLE
            diaryContent.visibility = View.VISIBLE
            diaryContent.text = "${str}" // textView에 str 출력

            save_Btn.visibility = View.INVISIBLE
            cha_Btn.visibility = View.VISIBLE
            del_Btn.visibility = View.VISIBLE


            if (diaryContent.getText() == "") {
                diaryContent.visibility = View.INVISIBLE
                today.visibility = View.VISIBLE
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                contextEditText.visibility = View.VISIBLE
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun saveDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = activity?.openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = contextEditText.text.toString()
            fos?.write(content.toByteArray())
            fos?.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    @SuppressLint("WrongConstant")
    fun removeDiary(readyDay: String) {

        var fos: FileOutputStream? = null

        try {

            fos = activity?.openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = ""
            fos?.write(content.toByteArray())
            fos?.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //권한 유무 체크
    private fun checkPermission() {
        val mediaPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        val permissionResult =
            ContextCompat.checkSelfPermission(this.requireContext(), mediaPermission)

        when (permissionResult) {
            PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }

            PackageManager.PERMISSION_DENIED -> {
                requestPermission()
            }
        }
    }

    //권한 요청
    private fun requestPermission() {
        //ActivityCompat.requestPermissions을 사용하면 사용자에게 권한을 요청하는 팝업을 보여줍니다.
        //사용자가 선택한 값은 onRequestPermissionsResult메서드를 통해서 전달되어 집니다.
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            100
        )
    }


    //갤러리 열기
    private fun openGallery() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, OPEN_GALLERY)

        startActivityForResult(Intent.createChooser(intent, "Load Picture"), Gallery)
    }



    //권한 요청 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            99 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(this.requireContext(), "Permission Denied", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }


}
