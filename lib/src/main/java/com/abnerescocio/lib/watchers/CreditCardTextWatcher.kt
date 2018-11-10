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
        s?.toString()?.replace(" ", "")?.let { strCardNumber ->
            val intCardNumber = strCardNumber.toInt()
            when (strCardNumber.length) {
                1 -> {
                    when (intCardNumber) {
                        4 -> {
                            add(2, R.drawable.ic_visa)
                        }
                    }
                }
                2 -> {
                    when (intCardNumber) {
                        38, 60 -> {
                            add(2, R.drawable.ic_hipercard)
                        }
                        in 23..26, in 51..55 -> {
                            add(2, R.drawable.ic_mastercard)
                        }
                    }
                }
                3 -> {
                    when (intCardNumber) {
                        in 223..229, 270, 271 -> {
                            add(2, R.drawable.ic_mastercard)
                        }
                    }
                }
                4 -> {
                    when (intCardNumber) {
                        in 2221..2229, 2720 -> {
                            add(2, R.drawable.ic_mastercard)
                        }
                    }
                }
            }
        }
    }

    private val pool = mutableMapOf<Int, Int>()

    fun add(id: Int, iconId: Int) {
        pool[id] = iconId
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconId, 0)
    }
}