package com.kdy.diary;


import com.kdy.diary.vo.DiaryVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DiaryArray {

    ArrayList<DiaryVO> allDiaryOfMonth=new ArrayList<DiaryVO>();

    public DiaryArray(String date /*yyyy-MM-dd 포맷이어야한다*/, String path /*String path = getFilesDir().getAbsolutePath();액티비티 내에서만 사용가능함*/) throws ParseException {

        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(dateFormat.parse(date));

        final int startDayOfMonth= 1;
        final int endDayOfMonth=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar fileDirCalendar= calendar;//폴더 이름 뽑아낼 용도로 만든 캘린더

        String content_file = "content.txt";//내용 파일명
        String title_file = "title.txt";//제목 파일명
        String sticker_file = "sticker.txt";//스티커 파일명
        String image_file = "image.png";//이미지 파일명: 20??-??-??-image.???(png든 jpg든)


        //1일부터 순서대로 담기!
        for(int i= startDayOfMonth; i <= endDayOfMonth; i++){
            fileDirCalendar.set(Calendar.DAY_OF_MONTH, i);//
            String dirName= dateFormat.format(fileDirCalendar);
            File dir=new File(path +File.pathSeparator+ dirName);
            if(dir.exists()){
                DiaryVO vo= new DiaryVO();
                vo.setTitle(readShowFiles(dir, title_file));
                vo.setContent(readShowFiles(dir, content_file));
                vo.setDate(dirName);
                vo.setStickerId(readShowFiles(dir,sticker_file));
                vo.setImage(new File(dir, image_file));//이미지 파일은 비트맵을 담아 보내기엔 너무 커서 걍 경로 타게팅된 상태로 보냄.
                allDiaryOfMonth.add(vo);
            }

        }

    }

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
            strData=filename+ "내용 없음";
        } catch (IOException e) {
            strData=filename+ "파일 길이 문제";
            e.printStackTrace();
        }finally {
            return strData;
        }

    }


}
