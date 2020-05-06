package com.example.mylotto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mylotto.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

val mylotto = arrayOf(0,0,0,0,0,0)

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
            mylotto[0] = Integer.parseInt(editText1.text.toString())
            mylotto[1] = Integer.parseInt(editText2.text.toString())
            mylotto[2] = Integer.parseInt(editText3.text.toString())
            mylotto[3] = Integer.parseInt(editText4.text.toString())
            mylotto[4] = Integer.parseInt(editText5.text.toString())
            mylotto[5] = Integer.parseInt(editText6.text.toString())
        }

        return root
    }
}
