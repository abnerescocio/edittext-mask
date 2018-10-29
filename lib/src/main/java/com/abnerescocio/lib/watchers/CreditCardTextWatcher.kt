package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView

class CreditCardTextWatcher(view: TextView) : AppTextWatcher(view) {

    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning && editable != null) {
            with(editable) {
                if (length == 5) insert(4, " ")
                if (length == 10) insert(9, " ")
                if (length == 15) insert(14, " ")
            }
        }
    }
}