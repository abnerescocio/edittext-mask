package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView

class CNPJTextWatcher(view: TextView) : AppTextWatcher(view) {
    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning) {
            if (editable?.length == 2) editable.insert(2, ".")
            if (editable?.length == 6) editable.insert(6, ".")
            if (editable?.length == 10) editable.insert(10, "/")
            if (editable?.length == 15) editable.insert(15, "-")
        }
    }
}