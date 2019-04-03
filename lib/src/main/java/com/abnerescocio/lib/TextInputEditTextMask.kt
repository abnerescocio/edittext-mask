package com.abnerescocio.lib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import java.util.regex.PatternSyntaxException

class TextInputEditTextMask(context: Context?, attributeSet: AttributeSet?)
    : TextInputEditText(context, attributeSet) {

    private var defStyleAttr: Int = 0
    private var typeArray: TypedArray? = null
    private var maskErrorMsg: String? = null
    private var isRequired: Boolean = false
    private var requiredErrorMsg: String? = null
    private var range: String? = null
    private var rangeErrorMsg: String? = null
    @Deprecated("Use fieldValidator() instead")
    var isValid: Boolean = true
        private set

    private var mask: MASK? = null

    private var currentWatcher: TextWatcher? = null

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs) {
        this.defStyleAttr = defStyleAttr
    }

    init {
        typeArray = context?.theme?.obtainStyledAttributes(attributeSet,
                R.styleable.TextInputEditTextMask, 0, 0)

        val maskIdentifier = typeArray?.getInt(R.styleable.TextInputEditTextMask_mask, 0)
        maskErrorMsg = typeArray?.getString(R.styleable.TextInputEditTextMask_mask_errorMsg)
        isRequired = typeArray?.getBoolean(R.styleable.TextInputEditTextMask_required, false) ?: false
        requiredErrorMsg = typeArray?.getString(R.styleable.TextInputEditTextMask_required_errorMsg)
        range = typeArray?.getString(R.styleable.TextInputEditTextMask_range)
        rangeErrorMsg = typeArray?.getString(R.styleable.TextInputEditTextMask_range_errorMsg)

        setMask(maskIdentifier)

        typeArray?.recycle()
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        fieldValidator()
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        fieldValidator()
        return super.requestFocus(direction, previouslyFocusedRect)
    }

    fun fieldValidator() : Boolean {
        val inputLayout = parent?.parent
        if (inputLayout is TextInputLayout) {
            if (isRequired && text.isNullOrEmpty()) {
                inputLayout.error = requiredErrorMsg ?: context.getString(R.string.required_field)
                return false
            } else if (isRequired && text.isNotEmpty()) {
                inputLayout.error = null
            }

            try {
                range?.let { pattern ->
                    if (Regex(pattern).matches(text)) inputLayout.error = null
                    else {
                        inputLayout.error = rangeErrorMsg ?: context.getString(R.string.invalid_range)
                        return false
                    }
                }
            } catch (e: PatternSyntaxException) {
                throw PatternSyntaxException("Range is not a valid regex. Valid ex.: [0-99], [2-6], [a-Z]", range, e.index)
            }

            mask?.let { m ->
                m.getRegex().matches(text).and(m.isValid(text)).let {
                    if (it) inputLayout.error = null
                    else inputLayout.error = maskErrorMsg ?: context.getString(m.getMessage())
                }
            }

            return inputLayout.error == null
        }
        return false
    }

    fun setMask(identifier: Int?) {
        removeTextChangedListener(currentWatcher)
        mask = MASK.valueOf(identifier)
        mask?.getMaxLength()?.let { filters = arrayOf(InputFilter.LengthFilter(it)) }
        mask?.getWatcher(this)?.let {
            currentWatcher = it
            addTextChangedListener(currentWatcher)
        }
    }

    companion object {
        const val EMAIL = 100
        const val PHONE = 200
        const val IP = 300
        const val WEB_URL = 400
        const val BRAZILIAN_CPF = 1003
        const val BRAZILIAN_CNPJ = 1004
        const val BRAZILIAN_CEP = 1005
    }
}