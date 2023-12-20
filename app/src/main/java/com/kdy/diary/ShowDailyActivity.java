package com.kdy.diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShowDailyActivity extends AppCompatActivity {

    Button show_next, show_back;
    TextView  show_content,show_title,show_date;
    ImageView show_image,show_sticker;


    //내장 메모리의 저장 위치는 현재 내 패키지명..data/data/dotua/files/날짜 명 폴더 아래 =
    String content_file="content.txt";//내용 파일명
    String title_file="title.txt";//제목 파일명
    String sticker_file="sticker.txt";//스티커 파일명
    String image_file="image.png";//이미지 파일명: 20??-??-??-image.???(png든 jpg든)


    private File getSaveFolder(String folderName) {
        String path = getFilesDir().getAbsolutePath();
        File dirOfToday = new File(path + File.pathSeparator + folderName);
        if(!dirOfToday.exists()) {
            dirOfToday.mkdirs();
        }

        return dirOfToday;
    }

    private Boolean isFolderExist(String folderName) {
        String path = getFilesDir().getAbsolutePath();
        File dirOfToday = new File(path + File.pathSeparator + folderName);
        return dirOfToday.exists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_daily);

        show_date=findViewById(R.id.show_date);
        show_next=findViewById(R.id.show_next);
        show_back=findViewById(R.id.show_back);
        show_content=findViewById(R.id.show_content);
        show_image=findViewById(R.id.show_image);
        show_title=findViewById(R.id.show_title);
        show_sticker=findViewById(R.id.show_sticker);

        show_back.setOnClickListener(pageClick);
        show_next.setOnClickListener(pageClick);


        String date=getIntent().getStringExtra("date");
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

        show_date.setText(date);
        try {
            SimpleDateFormat yearFormat= new SimpleDateFormat("yyyy");
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(dateFormat.parse(date));

            Toast.makeText(ShowDailyActivity.this, "날짜"+calendar.get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
            int year= calendar.get(Calendar.YEAR);
            int month= calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DAY_OF_MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(isFolderExist(date)) {
            File dir = getSaveFolder(date);


            try {
                //읽고 오기
                show_content.setText(readShowFiles(dir, content_file));
                show_title.setText(readShowFiles(dir, title_file));
                show_sticker.setImageResource(Integer.parseInt(readShowFiles(dir, sticker_file)));
                Bitmap bitmapimg = readImg(dir, image_file);
                show_image.setImageBitmap(bitmapimg);


            } catch (FileNotFoundException e) {
                Toast.makeText(ShowDailyActivity.this, "파일 없음", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        show_next.setOnClickListener(pageClick);
        show_back.setOnClickListener(pageClick);
    }

    View.OnClickListener pageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SimpleDateFormat simple_format = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = getIntent().getStringExtra("date");
            Intent intent1= new Intent(ShowDailyActivity.this, ShowDailyActivity.class);

            switch(v.getId()) {
                case R.id.show_next:
                    //convert

                    try {

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(simple_format.parse(dateString));

                        //nextday
                        calendar.add(Calendar.DATE, 1);
                        //convert
                        String netxtDate = simple_format.format(calendar.getTime());
                        //save

                        intent1.putExtra("date",netxtDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.show_back:
                    //convert
                    try {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(simple_format.parse(dateString));
                        //yesterday
                        calendar.add(Calendar.DATE, -1);
                        //convert
                        String prevDate = simple_format.format(calendar.getTime());
                        //save
                        intent1.putExtra("date", prevDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            //reload
            finish();//인텐트 종료
            overridePendingTransition(0, 0);//인텐트 효과 없애기
            //i = getIntent(); //인텐트
            startActivity(intent1); //액티비티 열기
            overridePendingTransition(0, 0);//인텐트 효과 없애기

        }
    };

    private String readShowFiles(File dir, String filename){
        String strData=null;

        try {
            File file=new File(dir,filename);
            FileInputStream inputStream = new FileInputStream(file); //파일이 없으면 FileNotFoundException예외
            byte[] temp = new byte[inputStream.available()]; //파일길이 측정하다가 예외가 발생하면 IOException예외발새
            inputStream.read(temp);
            strData = new String(temp);
            inputStream.close();
        } catch (FileNotFoundException e) {
            strData="파일 없음";
        } catch (IOException e) {
                strData="파일 길이 문제";
             e.printStackTrace();
        }finally {
            return strData;
        }

    }
    private Bitmap readImg(File dir, String filename) throws OutOfMemoryError, Exception {

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;


        File file = new File(dir, filename);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);

        float widthScale = options.outWidth / width;
        float heightScale = options.outHeight / height;
        float scale = widthScale > heightScale ? widthScale : heightScale;

        if (scale >= 8) {
            options.inSampleSize = 6;
        } else {
            switch ((int) (scale / 1)) {
                case 7:
                case 6:
                    options.inSampleSize = 6;
                    break;
                case 5:
                case 4:
                    options.inSampleSize = 4;
                    break;
                case 3:
                case 2:
                    options.inSampleSize = 2;
                    break;

                default:
                    options.inSampleSize = 1;
            }
        }

        options.inJustDecodeBounds=false;

        return BitmapFactory.decodeFile(file.getPath(), options);

    }

}