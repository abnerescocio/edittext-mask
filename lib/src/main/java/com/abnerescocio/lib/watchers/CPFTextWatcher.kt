package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView

class CPFTextWatcher(view: TextView) : AppTextWatcher(view) {
    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning) {
            if (editable?.length == 4) editable.insert(3, ".")
            if (editable?.length == 8) editable.insert(7, ".")
            if (editable?.length == 12) editable.insert(11, "-")
        }
    }
}