package com.kdy.diary;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SecurityActivity extends AppCompatActivity {

    ImageButton b_btn;
    Button btnSetLock, btnSetDelLock, btnChangePwd;
    boolean lock = true;

    AppLock appLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_main);

        appLock = new AppLock(this);

        b_btn = findViewById(R.id.b_btn);
        b_btn.setOnClickListener(click);

        btnSetLock = findViewById(R.id.btnSetLock);
        btnSetDelLock = findViewById(R.id.btnSetDelLock);
        btnChangePwd = findViewById(R.id.btnChangePwd);

        init();

        btnSetLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SecurityActivity.this,InputPasswordActivity.class);
                i.putExtra(AppLockConst.type, AppLockConst.ENABLE_PASSLOCK);
                startActivityForResult(i, AppLockConst.ENABLE_PASSLOCK);
            }
        });

        btnSetDelLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SecurityActivity.this, InputPasswordActivity.class);
                i.putExtra(AppLockConst.type, AppLockConst.DISABLE_PASSLOCK);
                startActivityForResult(i, AppLockConst.DISABLE_PASSLOCK);
            }
        });

        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SecurityActivity.this, InputPasswordActivity.class);
                i.putExtra(AppLockConst.type, AppLockConst.CHANGE_PASSWORD);
                startActivityForResult(i, AppLockConst.CHANGE_PASSWORD);
            }
        });

    }//onCreate()

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.b_btn:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case AppLockConst.ENABLE_PASSLOCK:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "암호 설정 완료", Toast.LENGTH_SHORT).show();
                    init();
                    lock = false;
                }
                break;
            case AppLockConst.DISABLE_PASSLOCK:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "암호 삭제 완료", Toast.LENGTH_SHORT).show();
                    init();
                }
                break;
            case AppLockConst.CHANGE_PASSWORD:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "암호 변경 완료", Toast.LENGTH_SHORT).show();
                    lock = false;

                }
                break;
            case AppLockConst.UNLOCK_PASSWORD:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "잠금 해제 됨", Toast.LENGTH_SHORT).show();
                    lock = false;
                }
                break;
        }//switch
    }//onActivityResult

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(lock && appLock.isPassLockSet()){
//            Intent i = new Intent(this, InputPasswordActivity.class);
//            i.putExtra(AppLockConst.type, AppLockConst.UNLOCK_PASSWORD);
//            startActivityForResult(i, AppLockConst.UNLOCK_PASSWORD);
//        }
//    }//onStart()
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(appLock.isPassLockSet()){
//            lock = true;
//        }
//    }

    private void init(){
        if(appLock.isPassLockSet()){
            btnSetLock.setEnabled(false);
            btnSetDelLock.setEnabled(true);
            btnChangePwd.setEnabled(true);
            lock = true;
        }else{
            btnSetLock.setEnabled(true);
            btnSetDelLock.setEnabled(false);
            btnChangePwd.setEnabled(false);
            lock = false;
        }
    }//init()
}