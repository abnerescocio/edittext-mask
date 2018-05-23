package com.abnerescocio.lib

import android.util.Patterns

enum class MASK(private val id: Int?) {

    EMAIL(TextInputEditTextMask.EMAIL) {

        override fun getStringResIdToNoMatch(): Int {
            return R.string.no_match_email
        }

        override fun getRegex(): Regex {
            return Regex(Patterns.EMAIL_ADDRESS.pattern())
        }

        override fun getHint(): String {
            return "email@domain.com"
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