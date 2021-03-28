package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var historyStorage = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addListeners()
    }

    /**
     * Adds event listeners to operators RadioGroup, number input fields and history toggle switch
     */
    private fun addListeners() {
        OperatorsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // sets text of operator text field according to radio button id
            when (checkedId) {
                radioplus.id -> operator.text = "+"
                radiominus.id -> operator.text = "-"
                radiomul.id -> operator.text = "*"
                radiodiv.id -> operator.text = "/"
            }
            calculator()
        }
        number1.addTextChangedListener(textWatcher)
        number2.addTextChangedListener(textWatcher)

        historySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                historyStorage.clear()
                historyShow.isEnabled = false
            } else {
                historyShow.isEnabled = true
            }
        }

        historyShow.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("history", historyStorage)
            startActivity(intent)
        }
    }

    /**
     * Listens for changes and triggers new calculation if text in number input fields is changed
     */
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            calculator()
        }
    }

    /**
     * Calculates result and writes it to result textView
     */
    private fun calculator() {
        if (OperatorsRadioGroup.checkedRadioButtonId == -1 || number1.text.isEmpty() || number2.text.isEmpty()) {
            result.text = "0"
            return
        }
        result.text = when (OperatorsRadioGroup.checkedRadioButtonId) {
            radioplus.id -> {
                val result = (number1.text.toString().toDouble() + number2.text.toString()
                    .toDouble()).toString()
                storeHistory("+", result)
                result
            }
            radiominus.id -> {
                val result = (number1.text.toString().toDouble() - number2.text.toString()
                    .toDouble()).toString()
                storeHistory("-", result)
                result
            }
            radiomul.id -> {
                val result = (number1.text.toString().toDouble() * number2.text.toString()
                    .toDouble()).toString()
                storeHistory("*", result)
                result
            }
            radiodiv.id -> {
                val result = (number1.text.toString().toDouble() / number2.text.toString()
                    .toDouble()).toString()
                storeHistory("/", result)
                result
            }
            else -> "calculation error"
        }
    }

    /**
     * Stores equation along with operator and result in ArrayList<String>
     * @param operator Operator that is used when calculating result
     * @param result Calculated result
     */
    private fun storeHistory(operator: String, result: String) {
        if (historySwitch.isChecked) {
            historyStorage.add(number1.text.toString() + operator + number2.text.toString() + "=" + result)
        }
    }
}