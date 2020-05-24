package com.example.mylotto.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mylotto.R
import com.example.mylotto.ui.dashboard.filename
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        val importBtn = root.findViewById<Button>(R.id.importBtn)
        importBtn.setOnClickListener {
            readFile()
        }

        return root
    }
    private fun readFile() {
        val file = File(context?.filesDir, filename)
        val temp = file.readLines()

        if (temp.isEmpty()) {
            val textView = TextView(context)
            textView.text = "읽어올 데이터가 없습니다."
            scrollView.addView(textView, 0)
        }
        else {
            for (index in temp.indices) {
                val textView = TextView(context)
                textView.text = "$index : ${temp[index]}"
                scrollView.addView(textView, 0)
            }
        }
    }
    private fun File.readLines(): List<String> {
        val result = ArrayList<String>()
        BufferedReader(InputStreamReader(FileInputStream(this))).forEachLine {result.add(it)}
        return result
    }
}
