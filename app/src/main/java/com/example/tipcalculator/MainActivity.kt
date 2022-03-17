package com.example.tipcalculator

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculator.databinding.ActivityMainBinding
import com.example.util.extension.onTextChange

const val HUNDRED_PERCENT = 100.00
const val TIP_INCREMENT_PERCENT = 5
const val MIN_TIP = 0
const val MAX_TIP = 95

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    private var numberOfPeople = 1 // set default number of people
    // private var tipPercent = 15 // set default tip percent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.addTipButton.setOnClickListener(this)
        binding.subtractTipButton.setOnClickListener(this)

        binding.tipTextView.onTextChange { input ->
            if (input.isNotEmpty()) {
                val tipStrip = input.replace("%", "")
                if (tipStrip.length == 2) {
                    val tipPercent: Int = tipStrip.toInt()
                    Log.i("TAG", "onTextChanged: $input $tipStrip")
                    calculateExpense(tipPercent)
                }
            }
        }

        binding.billAmount.onTextChange { }
    }

    private fun calculateExpense(tipPercent: Int) {
        if (TextUtils.isEmpty(binding.billAmount.text.toString())) {
            binding.billAmount.error = "Can't be empty.."
            return
        }
        val totalBill = binding.billAmount.text.toString().toDouble()

        val totalExpense = ((tipPercent / HUNDRED_PERCENT) * totalBill) / numberOfPeople
        val mTotalAmount = (totalBill) + totalExpense
        binding.tipAmount.text = String.format("$%.2f", totalExpense)
        binding.totalAmount.text = String.format("$%.2f", mTotalAmount)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addTipButton -> incrementTip()
            R.id.subtractTipButton -> decrementTip()
        }
    }

    private fun incrementTip() {
        var tipPercent: Int = binding.tipTextView.text.toString().trim().toInt()
        if (tipPercent != MAX_TIP) {
            tipPercent += TIP_INCREMENT_PERCENT
            binding.tipTextView.setText(String.format("%d%%", tipPercent))
        }
        calculateExpense(tipPercent = tipPercent)
    }

    private fun decrementTip() {
        var tipPercent: Int = binding.tipTextView.text.toString().trim().toInt()
        if (tipPercent != MIN_TIP) {
            tipPercent -= TIP_INCREMENT_PERCENT
            binding.tipTextView.setText(String.format("%d%%", tipPercent))
        }
        calculateExpense(tipPercent)
    }
}