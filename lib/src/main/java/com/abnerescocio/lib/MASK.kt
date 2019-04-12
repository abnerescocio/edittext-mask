package com.abnerescocio.lib

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
import android.widget.TextView
import com.abnerescocio.lib.watchers.CEPTextWatcher
import com.abnerescocio.lib.watchers.CNPJTextWatcher
import com.abnerescocio.lib.watchers.CPFTextWatcher

enum class MASK {

    EMAIL {

        override fun getId() = TextInputEditTextMask.EMAIL

        override fun getRegex(): Regex {
            return Regex(Patterns.EMAIL_ADDRESS.pattern())
        }

        override fun getMessage(): Int {
            return R.string.no_match_email
        }

        override fun getWatcher(view: TextView): TextWatcher? {
            return null
        }

        override fun getMaxLength(): Int? {
            return null
        }

        override fun isValid(char: CharSequence): Boolean {
            return true
        }

        override fun getInputType() = InputType.TYPE_CLASS_TEXT
    },

    PHONE {

        override fun getId() = TextInputEditTextMask.PHONE

        override fun getRegex() = Regex(Patterns.PHONE.pattern())

        override fun getMessage() = R.string.no_match_phone

        override fun getWatcher(view: TextView) = PhoneNumberFormattingTextWatcher()

        override fun getMaxLength(): Int? = null

        override fun isValid(char: CharSequence) = true

        override fun getInputType() = InputType.TYPE_CLASS_PHONE
    },

    CPF {

        override fun getId() = TextInputEditTextMask.BRAZILIAN_CPF

        override fun getRegex() = Regex(RGX_CPF)

        override fun getMessage() = R.string.no_match_brazilian_cpf

        override fun getWatcher(view: TextView) = CPFTextWatcher(view)

        override fun getMaxLength() = 11 + 3

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

        override fun getInputType() = InputType.TYPE_CLASS_PHONE
    },

    CNPJ {

        override fun getId() = TextInputEditTextMask.BRAZILIAN_CNPJ

        override fun getRegex() = Regex(RGX_CNPJ)

        override fun getMessage() = R.string.no_match_brazilian_cnpj

        override fun getWatcher(view: TextView) = CNPJTextWatcher(view)

        override fun getMaxLength() = 14 + 4

        override fun isValid(char: CharSequence): Boolean {
            val cnpj = char.replace(Regex("\\D"), "")
            if (cnpj.length < 14) return false

            var digit1 = 0
            for (i in 5 downTo 2) {
                digit1 += cnpj[5 - i].toString().toInt() * i
            }
            for (i in 9 downTo 2) {
                digit1 += cnpj[9 - i + 4].toString().toInt() * i
            }
            digit1 %= 11
            digit1 = if (digit1 < 2) 0 else 11 - digit1

            var digit2 = 0
            for (i in 6 downTo 2) {
                digit2 += cnpj[6 - i].toString().toInt() * i
            }
            for (i in 9 downTo 3) {
                digit2 += cnpj[9 - i + 5].toString().toInt() * i
            }
            digit2 += digit1 * 2
            digit2 %= 11
            digit2 = if (digit2 < 2) 0 else 11 - digit2

            return cnpj[12].toString().toInt() == digit1 && cnpj[13].toString().toInt() == digit2
        }

        override fun getInputType() = InputType.TYPE_CLASS_PHONE
    },

    CEP {

        override fun getId() = TextInputEditTextMask.BRAZILIAN_CEP

        override fun getRegex() = Regex(RGX_CEP)

        override fun getMessage() = R.string.no_match_brazilian_cep

        override fun getWatcher(view: TextView) = CEPTextWatcher(view)

        override fun getMaxLength() = 9

        override fun isValid(char: CharSequence) = true

        override fun getInputType() = InputType.TYPE_CLASS_PHONE

    },

    IP {

        override fun getId() = TextInputEditTextMask.IP

        override fun getRegex() = Regex(Patterns.IP_ADDRESS.pattern())

        override fun getMessage() = R.string.no_match_ip

        override fun getWatcher(view: TextView): TextWatcher? = null

        override fun getMaxLength(): Int? = null

        override fun isValid(char: CharSequence) = true

        override fun getInputType() = InputType.TYPE_NUMBER_FLAG_DECIMAL
    },

    WEB_URL {

        override fun getId() = TextInputEditTextMask.WEB_URL

        override fun getRegex() = Regex(Patterns.WEB_URL.pattern())

        override fun getMessage() = R.string.no_match_web_url

        override fun getWatcher(view: TextView): TextWatcher? = null

        override fun getMaxLength(): Int? = null

        override fun isValid(char: CharSequence) = true

        override fun getInputType() = InputType.TYPE_CLASS_TEXT
    };

    abstract fun getId(): Int
    abstract fun getRegex(): Regex
    abstract fun getMessage(): Int
    abstract fun getWatcher(view: TextView): TextWatcher?
    abstract fun getMaxLength(): Int?
    abstract fun isValid(char: CharSequence): Boolean
    abstract fun getInputType() : Int

    companion object {
        const val RGX_CPF = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"
        const val RGX_CNPJ = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}"
        const val RGX_CEP = "\\d{5}-\\d{3}"

        fun valueOf(id: Int?) = values().find { it.getId() == id }
                ?: throw ClassCastException("The id need be a valid MASK")
    }
}