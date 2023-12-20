package com.kdy.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ThemeActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    ImageButton b_btn;
    Button r_btn1, r_btn2;
    String themeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        r_btn1 = findViewById(R.id.r_btn1);
        r_btn2 = findViewById(R.id.r_btn2);
        b_btn = findViewById(R.id.b_btn);

        r_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeColor = com.kdy.diary.ThemeUtil.LIGHT_MODE;
                com.kdy.diary.ThemeUtil.applyTheme(themeColor);
                com.kdy.diary.ThemeUtil.modSave(getApplicationContext(),themeColor);

            }
        });

        r_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeColor = com.kdy.diary.ThemeUtil.DARK_MODE;
                com.kdy.diary.ThemeUtil.applyTheme(themeColor);
                com.kdy.diary.ThemeUtil.modSave(getApplicationContext(),themeColor);
            }
        });

        b_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}