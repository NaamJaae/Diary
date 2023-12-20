package com.kdy.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FontActivity extends AppCompatActivity {

    ImageButton b_btn;
    Button p_btn;
    SeekBar seekbar;
    TextView textview, txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font);

        b_btn = findViewById(R.id.b_btn);
        textview = findViewById(R.id.textview);
        txtResult = findViewById(R.id.txtResult);

        b_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });



    }

    public void mOnPopupClick(View v) {
        Intent intent = new Intent(FontActivity.this, PopupActivity.class);
        intent.putExtra("data", "Test Popup");
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                txtResult.setText(result);
            }
        }
    }

       // public void OnClickHandler(View v){

         //   AlertDialog.Builder builder = new AlertDialog.Builder(this);
           // builder.setTitle("폰트 선택");

            //builder.setItems(R.array.FONT, new DialogInterface.OnClickListener() {
            //  @Override
            //public void onClick(DialogInterface dialog, int which) {

            //}
            //})

}