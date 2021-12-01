package com.example.welt

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.annotation.SuppressLint
import android.widget.CalendarView
import android.widget.TextView
import com.example.welt.databinding.FragmentDiaryBinding
import java.io.FileInputStream
import java.io.FileOutputStream
import androidx.annotation.NonNull

import android.R
import android.app.Activity
import android.content.ContentResolver
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.util.Log
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_diary.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_diary.view.*

import java.io.InputStream
import android.graphics.drawable.BitmapDrawable

import java.io.ByteArrayOutputStream





class DiaryFragment : Fragment() {
    private lateinit var byteArray: ByteArray
    private lateinit var binding: FragmentDiaryBinding
    
    lateinit var fname: String
    lateinit var str: String
    lateinit var simage : String
//    private val OPEN_GALLERY = 1

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = database.reference

    private var uri: Uri? = null
    private val Gallery = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        val currentDate = currentDate()
        binding.today.text = currentDate.dateToString("yyyy년 MM월 dd일")

//        binding.uploadImage.setImageURI(uri)
//
//        binding.addPhotoBtn.setOnClickListener{
//            checkPermission()
//            funImageUpload(uri!!)
//
//        }

        test()

        return binding.root
    }

    private fun Date.dateToString(format: String, local: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, local)
        return formatter.format(this)
    }

    private fun currentDate(): Date {
        return Calendar.getInstance().time
    }


//    private fun funImageUpload(uri: Uri){
//        var fbStorage : FirebaseStorage? = FirebaseStorage.getInstance()
//
//        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        var imgFileName = "IMAGE_" + timeStamp + "_png"
//        var storageRef = fbStorage?.reference?.child("images")?.child(imgFileName)
//
//        storageRef?.putFile(uri!!)?.addOnSuccessListener {
//            Toast.makeText(activity, "사진이 업로드되었습니다." , Toast.LENGTH_SHORT).show()
//        }?.addOnFailureListener{
//            println(it)
//            Toast.makeText(activity, "사진 업로드에 실패하였습니다." , Toast.LENGTH_SHORT).show()
//
//        }
//    }

    private fun updateFirebase() {





    }


    //날짜 클릭 및 저장 버튼
    private fun test() {
        val user = FirebaseAuth.getInstance().currentUser
        val userID = user?.uid

        binding.calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            binding.today.text = String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth)


            binding.today.visibility = View.VISIBLE
            binding.contextEditText.visibility = View.VISIBLE
            binding.saveBtn.visibility = View.VISIBLE
            binding.diaryContent.visibility = View.INVISIBLE
            binding.chaBtn.visibility = View.INVISIBLE
            binding.delBtn.visibility = View.INVISIBLE
            binding.addPhotoBtn.visibility = View.VISIBLE


            if (userID != null) {
                checkDay(year, month, dayOfMonth, userID)
            }


            contextEditText.setText("")


            binding.saveBtn.setOnClickListener {

                saveDiary(fname)
               // funImageUpload(uri!!)
                updateFirebase()

                str = contextEditText.getText().toString()

                myRef.child("User").child(userID.toString()).child("Diary")
                    .child(String.format("%d-%d-%d ", year, month + 1, dayOfMonth)).child("Diary_Text").setValue(str)
                Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()

//                val stream = ByteArrayOutputStream()
//                val bitmap = (binding.uploadImage.getDrawable() as BitmapDrawable).bitmap
//
//                val scale = (1024 / bitmap.width.toFloat())
//                val image_w = (bitmap.width * scale).toInt()
//                val image_h = (bitmap.height * scale).toInt()
//                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
//                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//                byteArray = stream.toByteArray()
//
//                simage = byteArray.toString()
//
//                myRef.child("User").child(userID.toString()).child("Diary")
//                    .child(String.format("%d-%d-%d ", year, month + 1, dayOfMonth)).child("Diary_Image").setValue(str)



                diaryContent.text = "${str}"

                binding.contextEditText.visibility = View.INVISIBLE
                binding.saveBtn.visibility = View.INVISIBLE
                binding.chaBtn.visibility = View.VISIBLE
                binding.delBtn.visibility = View.VISIBLE
                binding.diaryContent.visibility = View.VISIBLE
                binding.addPhotoBtn.visibility = View.INVISIBLE



            }

            binding.chaBtn.setOnClickListener { // 수정 버튼을 누를 시
                binding.contextEditText.visibility = View.VISIBLE
                binding.diaryContent.visibility = View.INVISIBLE
                binding.contextEditText.setText(str) // editText에 textView에 저장된 내용을 출력
                binding.saveBtn.visibility = View.VISIBLE
                binding.chaBtn.visibility = View.INVISIBLE
                binding.delBtn.visibility = View.INVISIBLE
                binding.addPhotoBtn.visibility = View.VISIBLE
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
                binding.addPhotoBtn.visibility = View.VISIBLE

                removeDiary(fname)
                myRef.child("User").child(userID.toString()).child("Diary")
                    .child(String.format("%d-%d-%d ", year, month + 1, dayOfMonth)).setValue(null)

                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

            binding.addPhotoBtn.setOnClickListener{
                checkPermission()
            }

        })


    }


    private fun checkDay(cYear: Int, cMonth: Int, cDay: Int, userID: String) {

        val user = FirebaseAuth.getInstance().currentUser
        val userID = user?.uid

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
            add_photo_Btn.visibility = View.INVISIBLE
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
                add_photo_Btn.visibility = View.VISIBLE
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

//
    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Gallery) {

                var ImageData : Uri? = data?.data

                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, ImageData)
                    upload_image.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            Log.d("ActivityResult", "something wrong")
        }
    }




}