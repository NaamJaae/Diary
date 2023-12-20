package com.kdy.diary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import java.io.File;
import androidx.fragment.app.Fragment;

import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {
    Button call_write, call_show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MaterialCalendarView materialCalendarView;

        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        materialCalendarView = v.findViewById(R.id.materialCalendar);
        materialCalendarView.addDecorators(
                new CalendarFragment.SaturdayDecorator(), new CalendarFragment.SundayDecorator(), new CalendarFragment.TodayDecorator(),
                new FutureDecorator(), new StickerDecorator(getActivity()));

        call_show = v.findViewById(R.id.call_show);
        call_write = v.findViewById(R.id.call_write);


        //기본 디폴트 날짜(현재 날짜)
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String defaultdate=dateFormat.format(materialCalendarView.getCurrentDate().getDate());

        call_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(getActivity(), WriteDailyActivity.class);
                intent1.putExtra("date", defaultdate);
                view.getContext().startActivity(intent1);
            }
        });
        call_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(getActivity(), ShowDailyActivity.class);
                intent2.putExtra("date", defaultdate);
                view.getContext().startActivity(intent2);
            }
        });

        //날짜 선택 후 이벤트
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {

            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {

                    if(getSaveFolder(dataFormat.format(date.getDate()))){//일기가 있을경우
                        Intent intent1= new Intent(getActivity(), ShowDailyActivity.class);
                        intent1.putExtra("date", dataFormat.format(date.getDate()));
                        startActivity(intent1);
                    }else{//일기 없을경우
                        Intent intent2= new Intent(getActivity(), WriteDailyActivity.class);
                        intent2.putExtra("date", dataFormat.format(date.getDate()));
                        startActivity(intent2);
                    }

                }catch(Exception e){

                }//try_catch
                //화면 전환을 위한 intent 준비
            }
        });

        return v;

    }//onCreate()

    //파일이 있는지 유무 확인
    private Boolean getSaveFolder(String folderName) {
        String path = getContext().getFilesDir().getAbsolutePath();
        File dirOfToday = new File(path + File.pathSeparator + folderName);
        if(!dirOfToday.exists()) {
            return false;
        }else {
            return true;
        }
    }

    //달력에 스티커 표시
    class StickerDecorator implements DayViewDecorator {
        private final Calendar calendar = Calendar.getInstance();
        String sticker_file = "sticker.txt";


        public StickerDecorator(Activity context) {

        }

        @Override
        //여기서 일기의 유무 + 감정 종류 확인?
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");

            //일기 있으면 true 없으면 false
            return getSaveFolder(dataFormat.format(day.getDate()));
        }

        @Override
        public void decorate(DayViewFacade view) {
            //일기스티커 읽어와서 뿌리기
            //지금은 그냥 스티커 아무거나 넣어놓음
            view.setSelectionDrawable(getResources().getDrawable(R.drawable.sticker_normal));
        }
    }

    //오늘 이후 날짜 선택 불가
    class FutureDecorator implements DayViewDecorator {
        private final Calendar calendar = Calendar.getInstance();
        private CalendarDay date;

        public FutureDecorator() {
            date = CalendarDay.today();
        }


        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            return day.getYear() > date.getYear() ||
                    (day.getYear() == date.getYear() && day.getMonth() > date.getMonth()) ||
                    (day.getYear() == date.getYear() && day.getMonth() == date.getMonth() && day.getDay() > date.getDay());
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.parseColor("#d2d2d2")));
            view.setDaysDisabled(true);
        }
    }//FutureDecorator

    //토요일
    class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }//SaturdayDecoretor

    //일요일
    class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }//SundayDecorator

    //오늘 날짜
    class TodayDecorator implements DayViewDecorator {
        private CalendarDay date;

        public TodayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }

    }
}