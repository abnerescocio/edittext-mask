package com.abnerescocio.edittextmask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.abnerescocio.lib.TextInputEditTextMask;

public final class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputEditTextMask textMask = findViewById(R.id.text_input_edit_text_mask);
        textMask.setMask(TextInputEditTextMask.BRAZILIAN_CNPJ);
        textMask.setMaskErrorMsg("CNPJ inválido");
        textMask.setRequired(false);
        textMask.setRequiredErrorMsg("CNPJ deve ser preenchido");
    }
}
