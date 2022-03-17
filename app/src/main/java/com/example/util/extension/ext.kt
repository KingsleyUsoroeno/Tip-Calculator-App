package com.example.util.extension

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.onTextChange(onTextChangeCallback: (input: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            onTextChangeCallback(cs.toString())
        }

        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
        }

        override fun afterTextChanged(arg0: Editable) {
        }
    })
}