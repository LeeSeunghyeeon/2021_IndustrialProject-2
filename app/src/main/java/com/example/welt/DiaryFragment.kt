package com.example.welt

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
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_diary.*
import java.text.SimpleDateFormat
import java.util.*


class DiaryFragment : Fragment(){
    private lateinit var binding: FragmentDiaryBinding
    var userID : String = "userID"
    lateinit var fname : String
    lateinit var str : String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        val currentDate = currentDate()
        binding.today.setText(currentDate.dateToString("yyyy년 MM월 dd일"))
        test()

        return binding.root
    }

    private fun Date.dateToString(format:String, local : Locale = Locale.getDefault()) : String{
        val formatter = SimpleDateFormat(format, local)
        return formatter.format(this)
    }

    private fun currentDate() : Date{
        return Calendar.getInstance().time
    }

    private fun test(){
        binding.calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            binding.today.text = String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth)

            binding.contextEditText.visibility = View.VISIBLE
            binding.saveBtn.visibility = View.VISIBLE
            binding.diaryContent.visibility = View.INVISIBLE
            binding.chaBtn.visibility = View.INVISIBLE
            binding.delBtn.visibility = View.INVISIBLE

            //checkDay(year, month, dayOfMonth)



            binding.saveBtn.setOnClickListener{
//            saveDiary(fname)
                Toast.makeText(context, "저장되었습니다." , Toast.LENGTH_SHORT).show()

                str = contextEditText.text.toString()

                diaryContent.text = "${str}"

                binding.contextEditText.visibility = View.INVISIBLE
                binding.saveBtn.visibility = View.INVISIBLE
                binding.chaBtn.visibility = View.VISIBLE
                binding.delBtn.visibility = View.VISIBLE
                binding.diaryContent.visibility = View.VISIBLE


            }

        })

    }

    private fun checkDay(cYear: Int, cMonth: Int, cDay: Int, userID: String) {
        fname = "" + userID + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"

        var fis: FileInputStream? = null

        try{
//            fis = openFileInput(fname)
//
//           var fileData = ByteArray(fis.available())
//
//           fis.read(fileData)
//           fis.close()

//           str = String(fileData)

            contextEditText.visibility = View.INVISIBLE
            diaryContent.visibility = View.VISIBLE
            diaryContent.text = "${str}" // textView에 str 출력

            save_Btn.visibility = View.INVISIBLE
            cha_Btn.visibility = View.VISIBLE
            del_Btn.visibility = View.VISIBLE

            cha_Btn.setOnClickListener { // 수정 버튼을 누를 시
                contextEditText.visibility = View.VISIBLE
                diaryContent.visibility = View.INVISIBLE
                contextEditText.setText(str) // editText에 textView에 저장된
// 내용을 출력
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                diaryContent.text = "${contextEditText.getText()}"
            }

            del_Btn.setOnClickListener {
                diaryContent.visibility = View.INVISIBLE
                contextEditText.setText("")
                contextEditText.visibility = View.VISIBLE
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                //removeDiary(fname)
                Toast.makeText(context, "삭제되었습니다." , Toast.LENGTH_SHORT).show()
            }

            if(diaryContent.getText() == ""){
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
}