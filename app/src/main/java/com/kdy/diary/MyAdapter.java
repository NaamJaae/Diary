package com.kdy.diary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import com.kdy.diary.vo.DiaryVO;

public class MyAdapter extends ArrayAdapter<DiaryVO> {

    Context context;
    ArrayList<DiaryVO> allDiaryOfMonth;
    int resource;



    public MyAdapter(Context context, int resource, ArrayList<DiaryVO> allDiaryOfMonth, ListView listView) {
        super(context, resource, allDiaryOfMonth);

        //context : ListViewActivity.this
        //resource : R.layout.list_form.xml
        //arr : ArrayList<> arr;

        this.context = context;
        this.allDiaryOfMonth = allDiaryOfMonth;
        this.resource = resource;

//        //리스트뷰에 클릭이벤트 감지자 등록
//        listView.setOnItemClickListener( click );
//
//        //리스트뷰에 롱클릭 이벤트 감지자 등록
//        listView.setOnItemLongClickListener( long_click );
    }//생성자

    //리스트뷰의 추가되거나 삭제되는 항목을 갱신하는 메서드
    //액티비티에서 myList.setAdapter(adapter)를 호출할때 마다 생성자 파라미터로 넘어온 arr의 사이즈만큼
    //자동으로 실행되는 메서드(getView())
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //xml문서를 view로 변경해주는 클래스
        LayoutInflater linf =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = linf.inflate(resource, null);

        TextView tv1 = convertView.findViewById(R.id.title);
        TextView tv2 = convertView.findViewById(R.id.contnet);
        TextView tv3 = convertView.findViewById(R.id.regidate);
        ImageView imageView= convertView.findViewById(R.id.photo);

        DiaryVO vo= allDiaryOfMonth.get(position);

        tv1.setText(vo.getTitle());
        tv2.setText(vo.getContent());
        tv3.setText(vo.getDate());

        String image_file = "image.png";
        try {
            Bitmap bitmapimg= readImg(vo.getImage(), image_file,parent);
            imageView.setImageBitmap(bitmapimg);
        } catch (Exception e) {
            imageView.setImageResource(R.drawable.marisal_book8);
        }


        return convertView;
    }//getView()


    private Bitmap readImg(File dir, String filename, ViewGroup parent) throws OutOfMemoryError, Exception {


        //Display display =
        //DisplayMetrics metrics = new DisplayMetrics();
        //display.getMetrics(metrics);
        int width = parent.getWidth();
        int height = parent.getHeight();


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


















































