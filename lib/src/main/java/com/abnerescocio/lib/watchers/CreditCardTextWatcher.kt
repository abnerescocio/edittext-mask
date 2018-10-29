package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView
import com.abnerescocio.lib.R

class CreditCardTextWatcher(private val view: TextView) : AppTextWatcher(view) {

    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning && editable != null) {
            with(editable) {
                if (length == 5) insert(4, " ")
                if (length == 10) insert(9, " ")
                if (length == 15) insert(14, " ")
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        s?.toString()?.replace(" ", "")?.let {
            when {
                "4" == it.getOrNull(0).toString() -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visa, 0)
                }
                "5" == it.getOrNull(0).toString() -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mastercard, 0)
                }
                "38" == "${it.getOrNull(0)}${it.getOrNull(1)}" || "60" == "${it.getOrNull(0)}${it.getOrNull(1)}" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_hipercard, 0)
                }
                else -> view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_red_24dp, 0)
            }
        }
    }
}