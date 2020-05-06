package com.example.mylotto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mylotto.R
import java.util.*

var myLotto: Array<Int> = arrayOf(0,0,0,0,0,0)

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val randomText = root.findViewById<TextView>(R.id.randomNumberText)
        val randomBtn = root.findViewById<Button>(R.id.randomNumberBtn)
        randomBtn.setOnClickListener {
            for (index1 in 0..5) {
                var num: Int = 0
                while (true) {
                    num = rand(1, 46)
                    var index2: Int = 0
                    for (index3 in 0..index1) {
                        index2 = index3
                        if (num == myLotto[index3]) break
                    }
                    if (index1 <= index2) {
                        myLotto[index1] = num
                        break
                    }
                }
            }
            myLotto.sort()
            var stringTemp = ""
            for (index in 0..5) {
                stringTemp += "${myLotto[index]}"
                if (index < 5) stringTemp += ", "
            }
            randomText.text = "Random Number\n$stringTemp"
        }
        return root
    }


    private fun rand(from: Int, to: Int) : Int {
        val dice = Random()
        return dice.nextInt(to - from) + from
    }
}
