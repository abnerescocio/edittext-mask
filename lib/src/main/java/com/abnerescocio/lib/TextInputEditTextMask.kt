package com.abnerescocio.lib

import android.content.Context
import android.content.res.TypedArray
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet

class TextInputEditTextMask(context: Context?, attributeSet: AttributeSet?)
    : TextInputEditText(context, attributeSet) {

    private var defStyleAttr: Int = 0
    private var typeArray: TypedArray? = null
    private var maskIdentifer: Int? = null
    var maskErrorMsg: String? = null
    var isRequired: Boolean? = false
    var requiredErrorMsg: String? = null

    private var mask: MASK? = null

    private var currentWatcher: TextWatcher? = null

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs) {
        this.defStyleAttr = defStyleAttr
    }

    init {
        typeArray = context?.theme?.obtainStyledAttributes(attributeSet,
                R.styleable.TextInputEditTextMask, 0, 0)

        maskIdentifer = typeArray?.getInt(R.styleable.TextInputEditTextMask_mask, 0)
        maskErrorMsg = typeArray?.getString(R.styleable.TextInputEditTextMask_mask_errorMsg)
        isRequired = typeArray?.getBoolean(R.styleable.TextInputEditTextMask_required, false)
        requiredErrorMsg = typeArray?.getString(R.styleable.TextInputEditTextMask_required_errorMsg)

        createMask()

        typeArray?.recycle()
    }

    private fun createMask() {
        removeTextChangedListener(currentWatcher)
        mask = MASK.valueOf(maskIdentifer)
        mask?.getMaxLenght()?.let { filters = arrayOf(InputFilter.LengthFilter(it)) }
        mask?.getWatcher(this)?.let {
            currentWatcher = it
            addTextChangedListener(currentWatcher)
        }
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        val inputLayout = parent?.parent
        if (inputLayout is TextInputLayout) {
            if (isRequired == true && text.isEmpty()) {
                inputLayout.error = requiredErrorMsg ?: context.getString(R.string.required_field)
                return
            } else if (isRequired == true && text.isNotEmpty()) {
                inputLayout.error = null
            }

            if (mask != null && text.isNotEmpty()) {
                mask?.getRegex()?.matches(text)?.and(mask?.isValid(text) ?: false)?.let {
                    if (it) inputLayout.error = null
                    else inputLayout.error = maskErrorMsg ?: mask?.getStringResIdToNoMatch()?.let { it1 -> context.getString(it1) }
                }
            } else if (text.isEmpty()) {
                inputLayout.error = null
            }
        }
    }

    @Target(AnnotationTarget.TYPE)
    annotation class MaskIdentifier(vararg val value: Int)

    fun setMask(identifier: @MaskIdentifier(EMAIL, PHONE, IP, WEB_URL, BRAZILIAN_CPF, BRAZILIAN_CNPJ) Int) {
        maskIdentifer = identifier
        createMask()
    }

    companion object {
        private const val TAG = "edittext-mask"

        const val EMAIL = 100
        const val PHONE = 200
        const val IP = 300
        const val WEB_URL = 400
        const val BRAZILIAN_CPF = 1003
        const val BRAZILIAN_CNPJ = 1004
    }
}