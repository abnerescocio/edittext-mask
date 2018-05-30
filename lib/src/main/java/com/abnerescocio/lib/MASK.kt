package com.abnerescocio.lib

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.TextWatcher
import android.util.Patterns
import android.widget.TextView
import com.abnerescocio.lib.watchers.CpfTextWatcher

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
    },

    CPF(TextInputEditTextMask.BRAZILIAN_CPF) {
        override fun getRegex(): Regex {
            return Regex("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
        }


        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_brazilian_cpf
        }

        override fun getWatcher(view: TextView): TextWatcher? {
            return CpfTextWatcher(view)
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
    };

    abstract fun getRegex(): Regex
    abstract fun getStringResIdToNoMatch(): Int
    abstract fun getWatcher(view: TextView): TextWatcher?

    companion object {
        fun valueOf(id: Int?): MASK? {
            return values().find { it.id == id }
        }
    }
}