package com.kdy.diary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;

    //잠금관련
    boolean lock = true, loading = true;
    AppLock appLock;

    //서랍 관련 변수들
    DrawerLayout drawer_layout;
    ImageButton btn_m;
    Button btn1, btn2, btn3, btn4;
    LinearLayout drawer;
    String themeColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        appLock = new AppLock(this);
        if(lock && appLock.isPassLockSet()){
            Intent i = new Intent(this, LockActivity.class);
            i.putExtra(AppLockConst.type, AppLockConst.UNLOCK_PASSWORD);
            startActivityForResult(i, AppLockConst.UNLOCK_PASSWORD);
        }

        //서랍 관련
        themeColor = ThemeUtil.modLoad(getApplicationContext());
        ThemeUtil.applyTheme(themeColor);

        drawer_layout = findViewById(R.id.drawer_layout);
        btn_m = findViewById(R.id.btn_m);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        drawer = findViewById(R.id.drawer);

        btn_m.setOnClickListener(click);
        btn1.setOnClickListener(click);
        btn2.setOnClickListener(click);



        //btn_page1 = findViewById(R.id.btn_page1);
        //btn_page2 = findViewById(R.id.btn_page2);

        pager = findViewById(R.id.pager);
        //ViewPager에 Adapter를 추가
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        //맨 처음 보여질 페이지 번호를 설정
        pager.setCurrentItem(0);
//        btn_page1.setSelected(true);

        //뷰페이저의 페이지가 전환되면 감지하는 감지자
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                btn_page1.setSelected(false);
//                btn_page2.setSelected(false);
//                switch(position) {
//                    case 0:
//                        btn_page1.setSelected(true);
//                        break;
//                    case 1:
//                        btn_page2.setSelected(true);
//                        break;
//                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }//onCreate()

    private void setSupportActionBar(Toolbar toolbar) {
    }

    View.OnClickListener click =new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_m:  // 설정버튼
                    drawer_layout.openDrawer(drawer);
                    break;

                case R.id.btn1: // 서랍 안 보안버튼
                    Intent i = new Intent(MainActivity.this , SecurityActivity.class);
                    startActivity(i);
                    break;

                case R.id.btn2: // 서랍 안 테마버튼
                    i = new Intent(MainActivity.this , ThemeActivity.class);
                    startActivity(i);
                    break;

                case R.id.btn3: // 서랍 안 폰트버튼
                    i = new Intent(MainActivity.this , FontActivity.class);
                    startActivity(i);
                    break;

            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case AppLockConst.UNLOCK_PASSWORD: //잠금해제
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "잠금 해제 됨", Toast.LENGTH_SHORT).show();
                    lock = false;
                }
                break;
        }//switch
    }//onActivityResult

    @Override
    protected void onStart() {
        super.onStart();
        //로딩관련
        if(loading == true) {
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
            loading = false;
        }
    }//onStart()

    @Override
    protected void onPause() {
        super.onPause();
        if(appLock.isPassLockSet()){
            lock = true;
        }
    }
}


