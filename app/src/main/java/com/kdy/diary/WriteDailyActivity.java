package com.kdy.diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WriteDailyActivity extends AppCompatActivity implements MyStickerDialogFragment.OnfinishListener {

    TextView write_date, write_content, write_title;
    ImageView write_image;
    Button write_back, write_save, write_sticker;


    DialogFragment dialog =null;

    //내장 메모리의 저장 위치는 현재 내 패키지명..data/data/dotua/files/날짜 명 폴더 아래 =
    String content_file = "content.txt";//내용 파일명
    String title_file = "title.txt";//제목 파일명
    String sticker_file = "sticker.txt";//스티커 파일명
    String image_file = "image.png";//이미지 파일명: 20??-??-??-image.???(png든 jpg든)


    private final String TAG = this.getClass().getSimpleName();

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                Uri uri = intent.getData();
                write_image.setImageURI(uri);

            }
        }
    });

    private int stickerId=R.drawable.sticker_normal;

    private File getSaveFolder(String folderName) {
        String path = getFilesDir().getAbsolutePath();
        File dirOfToday = new File(path + File.pathSeparator + folderName);
        if (!dirOfToday.exists()) {
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
    public void onStickerSelectListener(int stickerId) {
        //스티커 정해지면 호출되는 메소드
        this.stickerId = stickerId;
        write_sticker.setBackgroundResource(stickerId);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_daily);

        write_date = findViewById(R.id.write_date);
        write_image = findViewById(R.id.write_image);
        write_content = findViewById(R.id.write_content);
        write_back = findViewById(R.id.write_back);
        write_save = findViewById(R.id.write_save);
        write_title = findViewById(R.id.write_title);
        write_sticker = findViewById(R.id.write_sticker);

        //날짜 받아오기
        String date=getIntent().getStringExtra("date");
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

        //바로 날짜 입력하기
        write_date.setText(date);

        //해당 날짜의 폴더 만들기 혹은 이미 있는 폴더 내용 다 불러오기
        if(isFolderExist(date)) {
            try {
                Toast.makeText(WriteDailyActivity.this, date, Toast.LENGTH_SHORT).show();
                File dir = getSaveFolder(date);
                write_content.setText(readShowFiles(dir, content_file));
                write_title.setText(readShowFiles(dir, title_file));
                Bitmap bitmapimg= readImg(dir, image_file);
                write_image.setImageBitmap(bitmapimg);
                write_sticker.setBackgroundResource(Integer.parseInt(readShowFiles(dir, sticker_file)));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }




        //이미지 클릭리스너->
        write_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);

            }
        });

        //뒤로 가기 클릭리스너
        write_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //스티커 고르기 다이얼로그
        write_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new MyStickerDialogFragment();
                dialog.show(getSupportFragmentManager(), MyStickerDialogFragment.TAG);
            }
        });


        //저장 클릭리스너

        write_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File dir = getSaveFolder(date);

                    //사진 저장
                    File file = new File(getSaveFolder(date), image_file);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BitmapDrawable image = (BitmapDrawable) write_image.getDrawable();
                    Bitmap bitmap = image.getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 70, fileOutputStream);

                    //제목 저장
                    file = new File(getSaveFolder(date), title_file);
                    fileOutputStream = new FileOutputStream(file);
                    String title = write_title.getText().toString();
                    fileOutputStream.write(title.getBytes());
                    fileOutputStream.close();


                    //내용저장
                    file = new File(getSaveFolder(date), content_file);
                    fileOutputStream = new FileOutputStream(file);
                    String content = write_content.getText().toString();
                    fileOutputStream.write(content.getBytes());
                    fileOutputStream.close();


                    //스티커 저장은 저 아래 onStickerSelectListener에 따로 있음
                    file = new File(getSaveFolder(date), sticker_file);
                    fileOutputStream = new FileOutputStream(file);
                    String sticker= ""+stickerId;
                    fileOutputStream.write(sticker.getBytes());
                    fileOutputStream.close();


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Toast.makeText(WriteDailyActivity.this, date + "제목" + title_file + "\n 내용:" + content_file + "\n 스티커" + stickerId, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(WriteDailyActivity.this, MainActivity.class);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if((dialog != null) && dialog.isDetached())
            dialog.dismiss();

        dialog=null;

    }

    private String readShowFiles(File dir, String filename) {
        String strData = null;

        try {
            File file = new File(dir, filename);
            FileInputStream inputStream = new FileInputStream(file); //파일이 없으면 FileNotFoundException예외
            byte[] temp = new byte[inputStream.available()]; //파일길이 측정하다가 예외가 발생하면 IOException예외발새
            inputStream.read(temp);
            strData = new String(temp);
            inputStream.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "not file exist.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "파일 길이 문제.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
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

