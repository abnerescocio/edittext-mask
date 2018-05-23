package com.abnerescocio.lib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet

class TextInputEditTextMask(context: Context?, attributeSet: AttributeSet?)
    : TextInputEditText(context, attributeSet), TextWatcher {

    private var defStyleAttr: Int = 0
    private var typeArray: TypedArray? = null
    private var maskIdentifer: Int? = null

    private var mask: MASK? = null

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs) {
        this.defStyleAttr = defStyleAttr
    }

    init {
        typeArray = context?.theme?.obtainStyledAttributes(attributeSet,
                R.styleable.TextInputEditTextMask, 0, 0)
        maskIdentifer = typeArray?.getInt(R.styleable.TextInputEditTextMask_mask, 0)
        mask = MASK.valueOf(maskIdentifer)
        typeArray?.recycle()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) hint = mask?.getHint()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        val inputLayout = parent?.parent
        if (inputLayout is TextInputLayout) {
            if (mask != null && text?.length?.compareTo(0) == 1) {
                mask?.getRegex()?.matches(text)?.let {
                    if (it) inputLayout.error = null
                    else inputLayout.error = context.getString(mask?.getStringResIdToNoMatch()!!)
                }
            } else if (text?.length?.compareTo(0) == 0) inputLayout.error = null
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    companion object {
        const val EMAIL = 100
        const val EMAIL_GMAIL = 101
        const val BRAZILIAN_PHONE_NUMBER = 1001
        const val BRAZILIAN_RG = 1002
        const val BRAZILIAN_CPF = 1003
        const val UNITED_STATES_PHONE_NUMBER = 1101
    }
}