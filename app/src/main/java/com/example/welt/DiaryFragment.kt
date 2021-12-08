package com.example.welt

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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


//        binding.uploadImage.setImageURI(uri)
//
//        binding.addPhotoBtn.setOnClickListener{
//            checkPermission()
//            funImageUpload(uri!!)
//
//        }

        myRef = myRef.child(userID.toString()).child("UserInfo")

//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val baby_birth = snapshot.child("user_baby_name").getValue()
//
//                val startDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(currentDate.toString())
//                val endDate = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).parse(baby_birth.toString())
//                val remaining_days = (endDate.time - startDate.time) / (24 * 60 * 60 * 1000)
//
//                binding.calDday.setText("$remaining_days")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                println("error")
//            }
//
//        })
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

                    binding.calDday.setText("아이와 만나기까지 D - %s".format(remaining_days))
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


//    fun onNewToken(token: String?) {
//        Log.d(TAG, "new Token: $token")
//
//        // 토큰 값을 따로 저장해둔다.
//        val pref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
//        val editor = pref.edit()
//        editor.putString("token", token).apply()
//        editor.commit()
//
//        Log.i("로그: ", "성공적으로 토큰을 저장함")
//    }
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//        Log.d(TAG, "From: " + remoteMessage!!.from)
//
//        // Notification 메시지를 수신할 경우는
//        // remoteMessage.notification?.body!! 여기에 내용이 저장되어있다.
//        // Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)
//
//        if(remoteMessage.data.isNotEmpty()){
//            Log.i("바디: ", remoteMessage.data["body"].toString())
//            Log.i("타이틀: ", remoteMessage.data["title"].toString())
//            sendNotification(remoteMessage)
//        }
//
//        else {
//            Log.i("수신에러: ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
//            Log.i("data값: ", remoteMessage.data.toString())
//        }
//    }
//
//    private fun sendNotification(remoteMessage: RemoteMessage) {
//        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시되도록 함
//        val uniId: Int = (System.currentTimeMillis() / 7).toInt()
//
//        // 일회용 PendingIntent
//        // PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임한다.
//        val intent = Intent(this.context, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack 을 경로만 남긴다. A-B-C-D-B => A-B
//        val pendingIntent = PendingIntent.getActivity(this.context, uniId, intent, PendingIntent.FLAG_ONE_SHOT)
//
//        // 알림 채널 이름
//        val channelId = getString(R.string.firebase_notification_channel_id)
//
//        // 알림 소리
//        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//
//        // 알림에 대한 UI 정보와 작업을 지정한다.
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
//            .setContentTitle(remoteMessage.data["body"].toString()) // 제목
//            .setContentText(remoteMessage.data["title"].toString()) // 메시지 내용
//            .setAutoCancel(true)
//            .setSound(soundUri) // 알림 소리
//            .setContentIntent(pendingIntent) // 알림 실행 시 Intent
//
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // 오레오 버전 이후에는 채널이 필요하다.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        // 알림 생성
//        notificationManager.notify(uniId, notificationBuilder.build())
//    }

}
