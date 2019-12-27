package com.abnerescocio.lib

import android.content.Context
import android.content.res.TypedArray
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
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
        if (text.isNotBlank()) fieldValidator()
    }

    fun fieldValidator() : Boolean {
        if (isRequired && text.isNullOrEmpty()) {
            return showErrorOnLayout(requiredErrorMsg ?: context.getString(R.string.required_field))
        } else if (isRequired && text.isNotEmpty()) {
            showErrorOnLayout(null)
        }

        try {
            regexAuxiliary?.let { pattern ->
                if (Regex(pattern).matches(text)) showErrorOnLayout(null)
                else {
                    return showErrorOnLayout(regexAuxiliaryErrorMsg ?: context.getString(R.string.invalid_regex))
                }
            }
        } catch (e: PatternSyntaxException) {
            throw PatternSyntaxException("Regex is not a valid.", regexAuxiliary, e.index)
        }

        mask?.let { m ->
            if (m.isValid(text)) showErrorOnLayout(null)
            else {
                return showErrorOnLayout(maskErrorMsg ?: context.getString(m.getMessage()))
            }
        }
        return false
    }

    private fun showErrorOnLayout(error: String?): Boolean {
        val hasError = error != null
        val layoutToShowError: View? = getLayoutToShowError()
        if (layoutToShowError is TextInputLayout) {
            layoutToShowError.isErrorEnabled = hasError
            layoutToShowError.error = error
        } else if (layoutToShowError is EditText) {
            layoutToShowError.error = error
        }
        return hasError
    }

    private fun getLayoutToShowError(): View {
        return try {
            parent!!.parent as TextInputLayout
        } catch (e: Exception) {
            this
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