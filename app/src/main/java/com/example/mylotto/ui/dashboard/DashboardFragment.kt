package com.example.mylotto.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mylotto.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import com.example.mylotto.ui.home.str
import java.io.File


val myLotto = arrayOf(0,0,0,0,0,0)
const val filename = "myfile.txt"

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val resultBtn = root.findViewById<Button>(R.id.resultBtn)
        resultBtn.setOnClickListener {
            var temp: String = editText1.text.toString() + editText2.text.toString() +
                    editText3.text.toString() + editText4.text.toString() +
                    editText5.text.toString() + editText6.text.toString()
            temp = temp.trim()

            if (temp.isEmpty()) {
                Toast.makeText(container?.context, "값을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                myLotto[0] = Integer.parseInt(editText1.text.toString())
                myLotto[1] = Integer.parseInt(editText2.text.toString())
                myLotto[2] = Integer.parseInt(editText3.text.toString())
                myLotto[3] = Integer.parseInt(editText4.text.toString())
                myLotto[4] = Integer.parseInt(editText5.text.toString())
                myLotto[5] = Integer.parseInt(editText6.text.toString())
                resultView.text = returnResult(answer(str), myLotto)
            }
        }

        val saveBtn = root.findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {
            val dir = File(context?.filesDir, filename)
            if (!dir.exists()) dir.createNewFile()

            resultView.text = dir.toPath().toString()
            if (dir.canWrite()){
                val temp = dir.readLines()
                var no: Int
                if (temp.isEmpty()) {
                    no = 1
                }
                else no = temp.size+1

                val fileContents = "$no : ${editText1.text}, ${editText2.text}, ${editText3.text}," +
                        "${editText4.text}, ${editText5.text}, ${editText6.text}. ${returnResult(answer(str), myLotto)}\n"
                root.context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                    it.write(fileContents.toByteArray())
                }
            }
        }

        return root
    }
    private fun answer(str : String) : Array<Int> {
        // 이번주 당첨 로또 값을 배열 형태로 반환하는 함수
        val temp1: String = str.split(". ")[0].split(" ")[3]
        var winningAnswer: Array<Int> = arrayOf(0,0,0,0,0,0,0)
        var temp2: List<String> = temp1.split(",")
        for (index in 0..5) {
            if (index < 5) winningAnswer[index] = Integer.parseInt(temp2[index])
            else {
                winningAnswer[index] = Integer.parseInt(temp2[index].split("+")[0])
                winningAnswer[index+1] = Integer.parseInt(temp2[index].split("+")[1])
                break
            }
        }

        return winningAnswer
    }
    private fun returnResult(answer: Array<Int>, mine: Array<Int>): String {
        // 이번주 로또 등수를 확인하는 함수
        var count = 0
        var bonus = 0
        for (index in 0..5) {
            if (answer[index] == mine[index]) count++
            if (answer[6] == mine[index]) bonus++
        }

        var message: String
        if (count == 6) message = "1등"
        else if (count == 5 && bonus == 1) message = "2등"
        else if (count == 5) message = "3등"
        else if (count == 4) message = "4등"
        else if (count == 3) message = "5등"
        else message = "꽝!"

        return message
    }

}
