package com.abnerescocio.lib

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.TextWatcher
import android.util.Patterns
import android.widget.TextView
import com.abnerescocio.lib.watchers.CNPJTextWatcher
import com.abnerescocio.lib.watchers.CPFTextWatcher

enum class MASK(private val id: Int?) {

    EMAIL(TextInputEditTextMask.EMAIL) {
        override fun getRegex(): Regex {
            return Regex(Patterns.EMAIL_ADDRESS.pattern())
        }

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_email
        }

        override fun getWatcher(view: TextView): TextWatcher? {
            return null
        }

        override fun getMaxLenght(): Int? {
            return null
        }

        override fun isValid(char: CharSequence): Boolean {
            return true
        }
    },

    PHONE(TextInputEditTextMask.PHONE) {
        override fun getRegex(): Regex {
            return Regex(Patterns.PHONE.pattern())
        }

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_phone
        }

        override fun getWatcher(view: TextView): TextWatcher? {
            return PhoneNumberFormattingTextWatcher()
        }

        override fun getMaxLenght(): Int? {
            return null
        }

        override fun isValid(char: CharSequence): Boolean {
            return true
        }
    },

    CPF(TextInputEditTextMask.BRAZILIAN_CPF) {
        override fun getRegex(): Regex {
            return Regex(RGX_CPF)
        }

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_brazilian_cpf
        }

        override fun getWatcher(view: TextView): TextWatcher? {
            return CPFTextWatcher(view)
        }

        override fun getMaxLenght(): Int? {
            return 11 + 3
        }

        override fun isValid(char: CharSequence): Boolean {
            val cpf = char.replace(Regex("\\D"), "")
            if (cpf.length < 11) return false

            var digit1 = 0
            for (i in 10 downTo 2) {
                digit1 += cpf[10 - i].toString().toInt() * i
            }
            digit1 = (digit1 * 10) % 11
            if (digit1 == 10) digit1 = 0

            var digit2 = 0
            for (i in 11 downTo 3) {
                digit2 += cpf[11 - i].toString().toInt() * i
            }
            digit2 += digit1 * 2
            digit2 = (digit2 * 10) % 11

            return cpf[9].toString().toInt() == digit1 && cpf[10].toString().toInt() == digit2
        }
    },

    CNPJ(TextInputEditTextMask.BRAZILIAN_CNPJ) {
        override fun getRegex(): Regex {
            return Regex(RGX_CNPJ)
        }

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_brazilian_cnpj
        }

        override fun getWatcher(view: TextView): TextWatcher? {
            return CNPJTextWatcher(view)
        }

        override fun getMaxLenght(): Int? {
            return 14 + 4
        }

        override fun isValid(char: CharSequence): Boolean {
            return true
        }
    },

    IP(TextInputEditTextMask.IP) {
        override fun getRegex(): Regex {
            return Regex(Patterns.IP_ADDRESS.pattern())
        }

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_ip
        }

        override fun getWatcher(view: TextView): TextWatcher? {
            return null
        }

        override fun getMaxLenght(): Int? {
            return null
        }

        override fun isValid(char: CharSequence): Boolean {
            return true
        }
    },

    WEB_URL(TextInputEditTextMask.WEB_URL) {
        override fun getRegex(): Regex {
            return Regex(Patterns.WEB_URL.pattern())
        }

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_web_url
        }

        override fun getWatcher(view: TextView): TextWatcher? {
            return null
        }

        override fun getMaxLenght(): Int? {
            return null
        }

        override fun isValid(char: CharSequence): Boolean {
            return true
        }
    };

    abstract fun getRegex(): Regex
    abstract fun getStringResIdToNoMatch(): Int
    abstract fun getWatcher(view: TextView): TextWatcher?
    abstract fun getMaxLenght(): Int?
    abstract fun isValid(char: CharSequence): Boolean

    companion object {
        const val RGX_CPF = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"
        const val RGX_CNPJ = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}"

        fun valueOf(id: Int?): MASK? {
            return values().find { it.id == id }
        }
    }
}