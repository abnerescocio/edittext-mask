package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView

class CNPJTextWatcher(view: TextView) : AppTextWatcher(view) {
    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning) {
            if (editable?.length == 3) editable.insert(2, ".")
            if (editable?.length == 7) editable.insert(6, ".")
            if (editable?.length == 11) editable.insert(10, "/")
            if (editable?.length == 16) editable.insert(15, "-")
        }
    }
}