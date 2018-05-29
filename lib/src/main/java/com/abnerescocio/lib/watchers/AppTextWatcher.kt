package com.abnerescocio.lib.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

abstract class AppTextWatcher(private val view: TextView): TextWatcher {
    private var lengthBeforeChange : Int = 0
    final override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        lengthBeforeChange = s?.length!!
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    final override fun afterTextChanged(editable: Editable?) {
        view.removeTextChangedListener(this)
        onChangeEditable(editable, lengthBeforeChange > editable?.length!!).let {
            view.addTextChangedListener(this)
        }
    }

    abstract fun onChangeEditable(editable: Editable?, isCleaning: Boolean)
}