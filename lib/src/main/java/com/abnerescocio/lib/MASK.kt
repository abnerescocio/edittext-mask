package com.abnerescocio.lib

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
import android.widget.TextView
import com.abnerescocio.lib.watchers.CEPTextWatcher
import com.abnerescocio.lib.watchers.CNPJTextWatcher
import com.abnerescocio.lib.watchers.CPFTextWatcher
import com.abnerescocio.lib.watchers.CreditCardTextWatcher

enum class MASK {

    EMAIL {

        override fun getId() = TextInputEditTextMask.EMAIL

        override fun getRegex() = Regex(Patterns.EMAIL_ADDRESS.pattern())

        override fun getMessage() = R.string.no_match_email

        override fun getWatcher(view: TextView): TextWatcher? = null

        override fun getMaxLength(): Int? = null

        override fun isValid(text: CharSequence) = getRegex().matches(text)

        override fun getInputType() = InputType.TYPE_CLASS_TEXT
    },

    PHONE {

        override fun getId() = TextInputEditTextMask.PHONE

        override fun getRegex() = Regex(Patterns.PHONE.pattern())

        override fun getMessage() = R.string.no_match_phone

        override fun getWatcher(view: TextView) = PhoneNumberFormattingTextWatcher()

        override fun getMaxLength(): Int? = null

        override fun isValid(text: CharSequence) = getRegex().matches(text)

        override fun getInputType() = InputType.TYPE_CLASS_PHONE
    },
    
    CREDIT_CARD {
    
        override fun getId() = TextInputEditTextMask.CREDIT_CARD

        override fun getRegex() = Regex(RGX_CREDIT_CARD)
    
        override fun getMessage() = R.string.no_match_credit_card

        override fun getWatcher(view: TextView) = CreditCardTextWatcher(view)

        override fun getMaxLength() = 19

        override fun isValid(text: CharSequence) = getRegex().matches(text)
        
        override fun getInputType() = InputType.TYPE_CLASS_PHONE

    },

    CPF {
        override fun getId() = TextInputEditTextMask.BRAZILIAN_CPF

        override fun getRegex() = Regex(RGX_CPF)

        override fun getMessage() = R.string.no_match_brazilian_cpf

        override fun getWatcher(view: TextView) = CPFTextWatcher(view)

        override fun getMaxLength() = 11 + 3

        override fun isValid(text: CharSequence): Boolean {
            if (!getRegex().matches(text)) return false
            
            val cpf = text.replace(Regex("\\D"), "")
            if (cpf.length < 11) return false
            
            if (Regex("0{11}|1{11}|2{11}|3{11}|4{11}|5{11}|6{11}|7{11}|8{11}|9{11}").matches(cpf)) return false

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

        override fun isValid(text: CharSequence): Boolean {
            if (!getRegex().matches(text)) return false
            
            val cnpj = text.replace(Regex("\\D"), "")
            if (cnpj.length < 14) return false
    
            if (Regex("0{14}|1{14}|2{14}|3{14}|4{14}|5{14}|6{14}|7{14}|8{14}|9{14}").matches(cnpj)) return false

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

        override fun isValid(text: CharSequence) = getRegex().matches(text)

        override fun getInputType() = InputType.TYPE_CLASS_PHONE

    },

    IP {

        override fun getId() = TextInputEditTextMask.IP

        override fun getRegex() = Regex(Patterns.IP_ADDRESS.pattern())

        override fun getMessage() = R.string.no_match_ip

        override fun getWatcher(view: TextView): TextWatcher? = null

        override fun getMaxLength(): Int? = null

        override fun isValid(text: CharSequence) = getRegex().matches(text)

        override fun getInputType() = InputType.TYPE_NUMBER_FLAG_DECIMAL
    },

    WEB_URL {

        override fun getId() = TextInputEditTextMask.WEB_URL

        override fun getRegex() = Regex(Patterns.WEB_URL.pattern())

        override fun getMessage() = R.string.no_match_web_url

        override fun getWatcher(view: TextView): TextWatcher? = null

        override fun getMaxLength(): Int? = null

        override fun isValid(text: CharSequence) = getRegex().matches(text)

        override fun getInputType() = InputType.TYPE_CLASS_TEXT
    };

    abstract fun getRegex(): Regex
    abstract fun getMessage(): Int
    abstract fun getWatcher(view: TextView): TextWatcher?
    abstract fun getMaxLength(): Int?
    abstract fun isValid(text: CharSequence): Boolean
    abstract fun getInputType() : Int
    abstract fun getId() : Int

    companion object {
        const val RGX_CPF = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}"
        const val RGX_CNPJ = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}|\\d{14}"
        const val RGX_CEP = "\\d{5}-\\d{3}|\\d{8}"
        const val RGX_CREDIT_CARD = "\\d{4} \\d{4} \\d{4} \\d{4}"

        fun valueOf(id: Int?) = values().find { it.getId() == id }
                ?: throw ClassCastException("The id need be a valid MASK")
    }
}