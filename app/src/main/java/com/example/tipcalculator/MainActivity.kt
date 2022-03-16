package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.example.tipcalculator.databinding.ActivityMainBinding
import android.widget.Toast

const val HUNDRED_PERCENT = 100.00
const val TIP_INCREMENT_PERCENT = 5
const val MIN_TIP = 0
const val MAX_TIP = 95

class MainActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
    private lateinit var binding : ActivityMainBinding

    private var numberOfPeople = 1 // set default number of people
    private var tipPercent = 15 // set default tip percent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {

        binding.addTipButton.setOnClickListener(this)
       binding.subtractTipButton.setOnClickListener(this)
        binding.billAmount.addTextChangedListener(this)

        binding.tipTextView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                if(cs.toString().isNotEmpty()){
                    val tipStrip = cs.toString().replace("%", "");
                    if(tipStrip.length == 2){
                       // val tip: Int = cs.toString().replace("%", "").toInt()
                        Log.i("TAG", "onTextChanged: $cs $tipStrip")

                        if(tipStrip.toInt() > 15) incrementTip() else decrementTip() // 15 is default
                    }
                    //calculateExpense()
                }else{
                    Toast.makeText(applicationContext,"Input a value",Toast.LENGTH_SHORT).show()
                }
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                //Toast.makeText(applicationContext, "before text change", Toast.LENGTH_LONG).show()
            }

            override fun afterTextChanged(arg0: Editable) {
               // Toast.makeText(applicationContext, "after text change", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun calculateExpense() {

        if (TextUtils.isEmpty(binding.billAmount.text.toString())){
            binding.billAmount.error = "Can't be empty.."
            return
        }
        val totalBill = binding.billAmount.text.toString().toDouble()

        val totalExpense = ((tipPercent/ HUNDRED_PERCENT)* totalBill) / numberOfPeople
        val mTotalAmount = (totalBill) + totalExpense
        binding.tipAmount.text = String.format("$%.2f", totalExpense)
        binding.totalAmount.text = String.format("$%.2f",mTotalAmount)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addTipButton -> incrementTip()
            R.id.subtractTipButton -> decrementTip()
        }
    }
    private fun incrementTip() {

        if (tipPercent != MAX_TIP) {
            tipPercent += TIP_INCREMENT_PERCENT
           binding.tipTextView.setText(String.format("%d%%",tipPercent))
        }
        calculateExpense()
    }
    private fun decrementTip() {
        if (tipPercent != MIN_TIP) {
            tipPercent -= TIP_INCREMENT_PERCENT
            binding.tipTextView.setText(String.format("%d%%",tipPercent))
        }
        calculateExpense()
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (binding.billAmount.text.isNotEmpty()) {
            calculateExpense()
        }else{
            Toast.makeText(this,"Input a value",Toast.LENGTH_SHORT).show()
        }
    }
    override fun afterTextChanged(s: Editable?) {
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}