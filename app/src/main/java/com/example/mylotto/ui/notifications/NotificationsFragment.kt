package com.example.mylotto.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mylotto.R
import com.example.mylotto.ui.dashboard.filename
import kotlinx.android.synthetic.main.fragment_notifications.view.*
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
            readFile(root)
        }

        val resetBtn = root.findViewById<Button>(R.id.resetBtn)
        resetBtn.setOnClickListener {
            resetView(root)
        }

        return root
    }
    private fun readFile(root: View) {
        val dir = File(context?.filesDir, filename)
        if (!dir.exists()) dir.createNewFile()

        var recordView = root.findViewById<ScrollView>(R.id.recordView)
        if (!dir.canRead()) {
            var textView = TextView(context)
            textView.text = "읽어올 데이터가 없습니다."
            recordView.addView(textView, 0)
        }
        else {
            var temp:String = "atemk" // dir.readText()
            var textView = TextView(context)
            textView.text = temp
            recordView.addView(textView, 0)
//            for (index in temp.indices) {
//                val textView = TextView(context)
//                textView.text = "$index : ${temp[index]}"
//                scrollView.addView(textView, 0)
//            }
        }
    }
    private fun resetView(root: View) {
        var recordView = root.findViewById<ScrollView>(R.id.recordView)
        var a = recordView[0]


    }
    private fun File.readLines(): List<String> {
        val result = ArrayList<String>()
        BufferedReader(InputStreamReader(FileInputStream(this))).forEachLine {result.add(it)}
        return result
    }
}
