package com.kdy.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        txt = (TextView) findViewById(R.id.txt);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        txt.setText(data);
    }

    public void mOnClose(View v){

        Intent intent = new Intent();
        intent.putExtra("result", "ClosePopup");
        setResult(RESULT_OK, intent);
        finish();
    }
    public boolean onTouchEvent(MotionEvent event){
        // 바깥 레이어 클릭시 안딛히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    public void onBackPressed(){
        // 뒤로가기버튼 막기
        return;
    }
}