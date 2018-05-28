package com.abnerescocio.lib

import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Patterns

enum class MASK(private val id: Int?) {

    EMAIL(TextInputEditTextMask.EMAIL) {
        override fun getHint(): String {
            return "email@domain.com"
        }

        override fun getRegex(): Regex {
            return Regex(Patterns.EMAIL_ADDRESS.pattern())
        }

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_email
        }
    },

    PHONE(TextInputEditTextMask.PHONE) {
        override fun getHint(): String {
            return ""
        }

        override fun getRegex(): Regex {
            return Regex(Patterns.PHONE.pattern())
        }

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_phone
        }
    };

    abstract fun getHint(): String
    abstract fun getRegex(): Regex
    abstract fun getStringResIdToNoMatch(): Int

    companion object {
        fun valueOf(id: Int?): MASK? {
            return values().find { it.id == id }
        }
    }
}