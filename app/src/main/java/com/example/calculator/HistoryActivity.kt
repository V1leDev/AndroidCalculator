package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_history.*
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val historyData: ArrayList<String>? = intent.getStringArrayListExtra("history")
        if (historyData != null) {
            // writes historic calculation data to scroll view
            for (data in historyData) {
                val dataView = TextView(this)
                dataView.text = data
                dataView.textSize = 18F
                historyLayout.addView(dataView)
            }
        }
    }
}