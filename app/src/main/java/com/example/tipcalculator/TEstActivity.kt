package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class TEstActivity : AppCompatActivity() {
    lateinit var addButton: Button
    lateinit var minusButton : Button
    lateinit var editText : EditText
    lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        addButton = findViewById(R.id.add)
        minusButton = findViewById(R.id.minus)
        editText = findViewById(R.id.editText)
        textView = findViewById(R.id.textView)
        addButton.setOnClickListener { increaseInteger() }
        minusButton.setOnClickListener { decreaseInteger() }


    }

    fun increaseInteger(){
        result()
        display(editText.text.toString().toInt() + 1)
    }
    fun decreaseInteger(){
        result()
        display(editText.text.toString().toInt() - 1)
    }
    private fun display(number:Int){

        editText.setText("$number")
    }
    private fun result(){
        val total = editText.text.toString().toInt()
        textView.text = String.format("$%.2f",total)
    }
}