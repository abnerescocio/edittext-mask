package com.abnerescocio.lib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.AttributeSet

class TextInputEditTextMask(context: Context?, attributeSet: AttributeSet?)
    : TextInputEditText(context, attributeSet) {

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
        if (mask == MASK.PHONE) addTextChangedListener(PhoneNumberFormattingTextWatcher())
        typeArray?.recycle()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) hint = mask?.getHint()
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        val inputLayout = parent?.parent
        if (inputLayout is TextInputLayout) {
            if (mask != null && text?.length?.compareTo(LENGTH_ZERO) == GREATER_THAN) {
                mask?.getRegex()?.matches(text)?.let {
                    if (it) inputLayout.error = null
                    else inputLayout.error = context.getString(mask?.getStringResIdToNoMatch()!!)
                }
            } else if (text?.length?.compareTo(LENGTH_ZERO) == EQUALS_THAN) inputLayout.error = null
        }
    }

    companion object {
        private const val TAG = "edittext-mask"

        private const val LENGTH_ZERO = 0
        private const val GREATER_THAN = 1
        private const val EQUALS_THAN = 1

        const val EMAIL = 100
        const val EMAIL_GMAIL = 101
        const val PHONE = 200
        const val BRAZILIAN_PHONE_NUMBER = 1001
        const val BRAZILIAN_RG = 1002
        const val BRAZILIAN_CPF = 1003
        const val UNITED_STATES_PHONE_NUMBER = 1101
    }
}