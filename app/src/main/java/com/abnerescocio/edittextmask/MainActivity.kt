package com.abnerescocio.edittextmask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.abnerescocio.lib.TextInputEditTextMask
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_input_edit_text_mask.setMask(TextInputEditTextMask.BRAZILIAN_CNPJ)
    }
}
