package com.kdy.diary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LockActivity extends AppCompatActivity {
    int n = -1;
    SharedPreferences pref;
    AppLock appLock;

    private String oldPwd = "";
    private boolean changePwdUnlock = false;
    TextView etInputInfo;
    LinearLayout ll_passcodes;
    EditText etPasscode1, etPasscode2, etPasscode3, etPasscode4;
    TableLayout tl_keys;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnClear, btnErase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_password);

        appLock = new AppLock(this);
        pref = getSharedPreferences("PWD", MODE_PRIVATE);

        etInputInfo = findViewById(R.id.etInputInfo);
        ll_passcodes = findViewById(R.id.ll_passcodes);
        etPasscode1 = findViewById(R.id.etPasscode1);
        etPasscode2 = findViewById(R.id.etPasscode2);
        etPasscode3 = findViewById(R.id.etPasscode3);
        etPasscode4 = findViewById(R.id.etPasscode4);
        tl_keys = findViewById(R.id.tl_keys);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnClear = findViewById(R.id.btnClear);
        btnErase = findViewById(R.id.btnErase);

        //버튼에 클릭이벤트
        btn0.setOnClickListener(click);
        btn1.setOnClickListener(click);
        btn2.setOnClickListener(click);
        btn3.setOnClickListener(click);
        btn4.setOnClickListener(click);
        btn5.setOnClickListener(click);
        btn6.setOnClickListener(click);
        btn7.setOnClickListener(click);
        btn8.setOnClickListener(click);
        btn9.setOnClickListener(click);
        btnClear.setOnClickListener(click);
        btnErase.setOnClickListener(click);
    }//onCreate()

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int currentValue = -1;

            switch(view.getId()){
                case R.id.btn0:
                    currentValue = 0;
                    break;
                case R.id.btn1:
                    currentValue = 1;
                    break;
                case R.id.btn2:
                    currentValue = 2;
                    break;
                case R.id.btn3:
                    currentValue = 3;
                    break;
                case R.id.btn4:
                    currentValue = 4;
                    break;
                case R.id.btn5:
                    currentValue = 5;
                    break;
                case R.id.btn6:
                    currentValue = 6;
                    break;
                case R.id.btn7:
                    currentValue = 7;
                    break;
                case R.id.btn8:
                    currentValue = 8;
                    break;
                case R.id.btn9:
                    currentValue = 9;
                    break;
                case R.id.btnClear:
                    onClear();
                    break;
                case R.id.btnErase:
                    onDeleteKey();
                    break;
            }//switch

            //입력된 번호 String으로 전환
            String strCurrentValue = String.valueOf(currentValue);

            //비밀번호를 EditText에 전달하면서 포커스 옮기기
            if(currentValue != -1){
                switch(getCurrentFocus().getId()){
                    case R.id.etPasscode1:
                        etPasscode1.setText(strCurrentValue);
                        etPasscode2.requestFocus();
                        break;
                    case R.id.etPasscode2:
                        etPasscode2.setText(strCurrentValue);
                        etPasscode3.requestFocus();
                        break;
                    case R.id.etPasscode3:
                        etPasscode3.setText(strCurrentValue);
                        etPasscode4.requestFocus();
                        break;
                    case R.id.etPasscode4:
                        etPasscode4.setText(strCurrentValue);
                        break;
                }//switch
            }

            //비밀번호 4자리 모두 입력시
            if(!etPasscode4.getText().toString().trim().isEmpty()){
                Intent i = getIntent();
                inputType(i.getIntExtra("type", 0));
            }
        }
    };//View.OnClickListener click

    //btnErase눌렀을때
    private void onDeleteKey(){
        if(etPasscode1.isFocused()){
            etPasscode1.setText("");
            return;
        }else if(etPasscode2.isFocused()){
            etPasscode1.setText("");
            etPasscode1.requestFocus();
            return;
        }else if(etPasscode3.isFocused()){
            etPasscode2.setText("");
            etPasscode2.requestFocus();
            return;
        }else if(etPasscode4.isFocused()){
            etPasscode3.setText("");
            etPasscode3.requestFocus();
            return;
        }
    }

    //btnClear눌렀을때
    private void onClear(){
        etPasscode1.setText("");
        etPasscode2.setText("");
        etPasscode3.setText("");
        etPasscode4.setText("");
        etPasscode1.requestFocus();
    }

    private String inputedPassword(){
        String pwd = etPasscode1.getText().toString()
                + etPasscode2.getText().toString()
                + etPasscode3.getText().toString()
                + etPasscode4.getText().toString();
        return pwd;
    }

    private void inputType(int type){
        switch(type){
            case AppLockConst.UNLOCK_PASSWORD: //잠금해제
                if(appLock.checkPassLock(inputedPassword())){
                    setResult(Activity.RESULT_OK);
                    finish();
                }else{
                    etInputInfo.setText("비밀번호가 틀립니다");
                    onClear();
                }
                break;
        }//switch
    }

    @Override public void onBackPressed() {
        //super.onBackPressed();
    }
}