package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView
import com.abnerescocio.lib.R

class CreditCardTextWatcher(private val view: TextView) : AppTextWatcher(view) {

    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning) {
            if (editable?.length == 5) editable.insert(4, " ")
            if (editable?.length == 10) editable.insert(9, " ")
            if (editable?.length == 15) editable.insert(14, " ")
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        s?.toString()?.replace(" ", "")?.let { strCardNumber ->
            when (strCardNumber.length) {
                1 -> {
                    when (strCardNumber.toInt()) {
                        4 -> {
                            setDrawable(R.drawable.ic_visa)
                        }
                    }
                }
                2 -> {
                    when (strCardNumber.toInt()) {
                        38, 60 -> {
                            setDrawable(R.drawable.ic_hipercard)
                        }
                        in 23..26, in 51..55 -> {
                            setDrawable(R.drawable.ic_mastercard)
                        }
                    }
                }
                3 -> {
                    when (strCardNumber.toInt()) {
                        in 223..229, 270, 271 -> {
                            setDrawable(R.drawable.ic_mastercard)
                        }
                    }
                }
                4 -> {
                    when (strCardNumber.toInt()) {
                        in 2221..2229, 2720 -> {
                            setDrawable(R.drawable.ic_mastercard)
                        }
                    }
                }
            }
        }
    }

    private fun setDrawable(iconId: Int) {
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconId, 0)
    }
}