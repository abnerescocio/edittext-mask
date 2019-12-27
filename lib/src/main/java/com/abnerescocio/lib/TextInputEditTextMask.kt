package com.abnerescocio.lib

import android.content.Context
import android.content.res.TypedArray
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import com.abnerescocio.lib.watchers.CreditCardTextWatcher
import java.util.regex.PatternSyntaxException

class TextInputEditTextMask(context: Context?, attributeSet: AttributeSet?)
    : TextInputEditText(context, attributeSet) {

    private var defStyleAttr: Int = 0
    private var typeArray: TypedArray? = null
    var maskErrorMsg: String? = null
    var isRequired: Boolean = false
    var requiredErrorMsg: String? = null
    var regexAuxiliary: String? = null
    var regexAuxiliaryErrorMsg: String? = null
    @Suppress("unused")
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

        val maskIdentifier = typeArray?.getInt(R.styleable.TextInputEditTextMask_mask, DEFAULT)
        maskErrorMsg = typeArray?.getString(R.styleable.TextInputEditTextMask_mask_errorMsg)
        isRequired = typeArray?.getBoolean(R.styleable.TextInputEditTextMask_required, false) ?: false
        requiredErrorMsg = typeArray?.getString(R.styleable.TextInputEditTextMask_required_errorMsg)
        regexAuxiliary = typeArray?.getString(R.styleable.TextInputEditTextMask_regex_auxiliary)
        regexAuxiliaryErrorMsg = typeArray?.getString(R.styleable.TextInputEditTextMask_regex_auxiliary_errorMsg)

        setMask(maskIdentifier)

        typeArray?.recycle()
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        fieldValidator()
    }

    fun fieldValidator() : Boolean {
        val inputLayout = parent?.parent
        if (inputLayout is TextInputLayout) {
            if (isRequired && text.isNullOrEmpty()) {
                setErrorOnParent(requiredErrorMsg ?: context.getString(R.string.required_field))
                return false
            } else if (isRequired && text.isNotEmpty()) {
                setErrorOnParent(null)
            }

            try {
                regexAuxiliary?.let { pattern ->
                    if (Regex(pattern).matches(text)) setErrorOnParent(null)
                    else {
                        setErrorOnParent(regexAuxiliaryErrorMsg ?: context.getString(R.string.invalid_regex))
                        return false
                    }
                }
            } catch (e: PatternSyntaxException) {
                throw PatternSyntaxException("Regex is not a valid.", regexAuxiliary, e.index)
            }

            mask?.let { m ->
                if (m.isValid(text)) setErrorOnParent(null)
                else setErrorOnParent(maskErrorMsg ?: context.getString(m.getMessage()))
            }

            return inputLayout.error == null
        }
        return false
    }

    private fun setErrorOnParent(error: String?) {
        try {
            val textInputLayout = parent.parent as TextInputLayout
            textInputLayout.isErrorEnabled = error != null
            textInputLayout.error = error
        } catch (e: java.lang.ClassCastException) {
            e.printStackTrace()
        }
    }

    fun setMask(identifier: Int?) {
        if (currentWatcher is CreditCardTextWatcher) setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        removeTextChangedListener(currentWatcher)
        if (identifier != DEFAULT) mask = MASK.valueOf(identifier)
        mask?.getMaxLength().let {
            filters = if (it != null) arrayOf(InputFilter.LengthFilter(it)) else arrayOf()
        }
        mask?.getInputType()?.let { inputType = it }
        mask?.getWatcher(this)?.let {
            currentWatcher = it
            addTextChangedListener(currentWatcher)
        }
    }

    companion object {
        const val DEFAULT = 0
        const val EMAIL = 100
        const val PHONE = 200
        const val IP = 300
        const val WEB_URL = 400
        const val CREDIT_CARD = 500
        const val BRAZILIAN_CPF = 1003
        const val BRAZILIAN_CNPJ = 1004
        const val BRAZILIAN_CEP = 1005
    }
}