package com.abnerescocio.edittextmask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.abnerescocio.lib.MASK
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MaskAdapter.OnListInteraction {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        is_required.setOnCheckedChangeListener { compoundButton, b ->
            text_input_edit_text_mask.isRequired = b
            text_input_edit_text_mask.fieldValidator()
        }

        required_message.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text_input_edit_text_mask.requiredErrorMsg = p0.toString()
            }

        })

        masks.adapter = MaskAdapter(MASK.values(), onListInteraction = this)
    }

    override fun onClick(mask: MASK) {
        text_input_edit_text_mask.setMask(mask.getId())
        text_input_edit_text_mask.maskErrorMsg = getString(mask.getMessage())
        text_input_edit_text_mask.fieldValidator()
    }
}
