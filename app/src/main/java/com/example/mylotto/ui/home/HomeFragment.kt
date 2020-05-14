package com.example.mylotto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mylotto.R
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import java.util.*

const val address = "https://dhlottery.co.kr/gameResult.do?method=byWin"
var randomLotto: Array<Int> = arrayOf(0,0,0,0,0,0)
lateinit var str: String

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

        val winningText = root.findViewById<TextView>(R.id.winningNumberText)
        doAsync {
            str = Jsoup.connect(address).get().select("meta")[2].attr("content").split(". ")[0]
            winningText.text = str
        }

        val randomText = root.findViewById<TextView>(R.id.randomNumberText)
        val randomBtn = root.findViewById<Button>(R.id.randomNumberBtn)
        randomBtn.setOnClickListener {
            for (index1 in 0..5) {
                var num: Int
                while (true) {
                    num = rand(1, 46)
                    var index2 = 0
                    for (index3 in 0..index1) {
                        index2 = index3
                        if (num == randomLotto[index3]) break
                    }
                    if (index1 <= index2) {
                        randomLotto[index1] = num
                        break
                    }
                }
            }
            randomLotto.sort()
            var stringTemp = ""
            for (index in 0..5) {
                stringTemp += "${randomLotto[index]}"
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
