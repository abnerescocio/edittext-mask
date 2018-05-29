package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView

class CpfTextWatcher(view: TextView) : AppTextWatcher(view) {
    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning) {
            if (editable?.length == 3) editable.insert(3, ".")
            if (editable?.length == 7) editable.insert(7, ".")
            if (editable?.length == 11) editable.insert(11, "-")
        }
    }
}