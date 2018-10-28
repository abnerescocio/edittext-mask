package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView

class CEPTextWatcher(view: TextView) : AppTextWatcher(view) {

    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning && editable != null) {
            with(editable) {
                if (length == 6) insert(5, "-")
            }
        }
    }
}