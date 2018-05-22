package com.abnerescocio.lib

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.util.AttributeSet

class TextInputEditTextMask(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
    : TextInputEditText(context, attributeSet, defStyleAttr) {

    constructor(context: Context): this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)


}