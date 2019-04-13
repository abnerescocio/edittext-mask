package com.abnerescocio.lib.watchers

import android.text.Editable
import android.widget.TextView
import com.abnerescocio.lib.R

class CreditCardTextWatcher(private val view: TextView) : AppTextWatcher(view) {
    
    private val poolOfIcons = hashMapOf<Int, Int?>()
    private var currentIndexIntoPool = 0

    override fun onChangeEditable(editable: Editable?, isCleaning: Boolean) {
        if (!isCleaning) {
            if (editable?.length == 5) editable.insert(4, " ")
            if (editable?.length == 10) editable.insert(9, " ")
            if (editable?.length == 15) editable.insert(14, " ")
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        val strCardNumber = s?.toString()?.replace(" ", "") ?: return
        when (strCardNumber.length) {
            INDEX1 -> {
                when (strCardNumber.toInt()) {
                    4 -> {
                        poolOfIcons[INDEX1] = R.drawable.ic_visa
                    }
                    else -> poolOfIcons[INDEX1] = null
                }
                setDrawableByIndexOfPool(INDEX1)
            }
            INDEX2 -> {
                when (strCardNumber.toInt()) {
                    38, 60 -> {
                        poolOfIcons[INDEX2] = R.drawable.ic_hipercard
                    }
                    in 23..26, in 51..55 -> {
                        poolOfIcons[INDEX2] = R.drawable.ic_mastercard
                    }
                    else -> poolOfIcons[INDEX2] = null
                }
                setDrawableByIndexOfPool(INDEX2)
            }
            INDEX3 -> {
                when (strCardNumber.toInt()) {
                    in 223..229, 270, 271 -> {
                        poolOfIcons[INDEX3] = R.drawable.ic_mastercard
                    }
                    else -> poolOfIcons[INDEX3] = null
                }
                setDrawableByIndexOfPool(INDEX3)
            }
            INDEX4 -> {
                when (strCardNumber.toInt()) {
                    in 2221..2229, 2720 -> {
                        poolOfIcons[INDEX4] = R.drawable.ic_mastercard
                    }
                    else -> poolOfIcons[INDEX4] = null
                }
                setDrawableByIndexOfPool(INDEX4)
            }
            else -> setDrawableByIndexOfPool(0)
        }
    }

    private fun setDrawableByIndexOfPool(indexOfPool: Int) {
        val itemId = poolOfIcons[indexOfPool]
        if (itemId != null) setDrawable(itemId)
        else {
            if (indexOfPool >= currentIndexIntoPool) {
                indexOfPool.minus(1).downTo(INDEX1).forEach { index ->
                    val lastItemId = poolOfIcons[index]
                    if (lastItemId != null) {
                        setDrawable(lastItemId)
                        return
                    } else setDrawable(0)
                }
            } else setDrawable(0)
        }
        currentIndexIntoPool = indexOfPool
    }
    
    private fun setDrawable(itemId: Int) {
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, itemId, 0)
    }
    
    companion object {
        private const val INDEX1 = 1
        private const val INDEX2 = 2
        private const val INDEX3 = 3
        private const val INDEX4 = 4
    }
}