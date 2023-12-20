package com.kdy.diary;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;

import com.kdy.diary.vo.DiaryVO;

public class ListFragment extends Fragment {
    Intent j;
    ListView myList, searchListView;
    EditText title, contnet, regidate, search, date;//et
    Button btn_add, btn_date;
    private ArrayList<DiaryVO> searchList;
    private HashSet<DiaryVO> insertList;
    ArrayList<DiaryVO> arr;
    ArrayList<DiaryVO> dateArr;

    private com.kdy.diary.MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_list, container, false);

        arr = new ArrayList<DiaryVO>();

        myList = v.findViewById(R.id.myList);
        searchListView = v.findViewById(R.id.myList);

        search = v.findViewById(R.id.et);
        date = v.findViewById(R.id.date);
        btn_add = v.findViewById(R.id.btn_add);
        btn_date = v.findViewById(R.id.btn_date);

        //검색 버튼, 검색기능 구현
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchList = new ArrayList<DiaryVO>();
                insertList = new HashSet<DiaryVO>();

                String text = search.getText().toString();

                //스플릿으로 잘라서 서치, 리스트에 넣기
                String[] splitText = text.split(" ");
                for (int i = 0; i < splitText.length; i++) {
                    search(splitText[i]);
                }
                //단어가 모두 들어가지 않는데 검색된 데이터 삭제
                for (int i = 0; i < splitText.length; i++) {
                    delete(splitText[i]);
                }

                convert();


            }
        });

        //날짜 검색 버튼, 검색기능 구현
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String d = date.getText().toString();
                if (!(date.getText().toString().equals("0000-00") || date.getText().toString().equals(""))){
                    if (date.getText().toString().equals("")) {
                        d = "0000-00";
                    }
                    try {
                        SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM"); //검증할 날짜 포맷 설정
                        dateFormatParser.setLenient(false); //false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
                        dateFormatParser.parse(d); //대상 값 포맷에 적용되는지 확인
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "yyyy-MM 형식으로 날짜를 입력하세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                String startDt = d;
                dateArr = new ArrayList<DiaryVO>();

                int startYear = Integer.parseInt(startDt.substring(0,4));
                int startMonth= Integer.parseInt(startDt.substring(5,7));

                Calendar arrDate = Calendar.getInstance();
                Calendar cal = Calendar.getInstance();
                Calendar endDt = Calendar.getInstance();

//             Calendar의 Month는 0부터 시작하므로 -1 해준다.
//             Calendar의 기본 날짜를 startDt로 셋팅해준다.
                arrDate.set(startYear, startMonth -1, 1);
                cal.set(startYear, startMonth -1, 1);

                if (startMonth == 12) {
                    endDt.set(startYear +1, 0, 1);
                }else{
                    endDt.set(startYear , startMonth, 1);
                }

                for(int j = 0; j < arr.size(); j++) {
                    if (getValidDate(cal, endDt, arr.get(j).getDate()))
                        dateArr.add(arr.get(j));
                }

                adapter = new com.kdy.diary.MyAdapter(getActivity(), R.layout.list_form, dateArr, myList);
                //준비된 어댑터를 ListView에 추가
                myList.setAdapter(adapter);

//                startActivity(i);
//                getActivity().finish();

            }
        });

        if( date.getText().toString().equals("0000-00") || j.getStringExtra("date").equals("")) {
            adapter = new com.kdy.diary.MyAdapter(getActivity(), R.layout.list_form, arr, myList);
            //준비된 어댑터를 ListView에 추가
            myList.setAdapter(adapter);
        }else{
//            Intent i = new Intent(getActivity(), ListViewActivity.class);
//            i.putExtra("date", date.getText().toString());
        }

        return v;
    }

    //////////////날짜 검색 관련 메소드/////////////
    static class SortByDate implements Comparator<DiaryVO> {
        @Override
        public int compare(DiaryVO a, DiaryVO b) {
            return a.getDate().compareTo(b.getDate());
        }
    }
    public static String getDateByString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    public static int getDateByInteger(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return Integer.parseInt(sdf.format(date));
    }


    private Boolean getValidDate(Calendar Start, Calendar End, String value) {
        int startYear = Integer.parseInt(value.substring(0,4));
        int startMonth= Integer.parseInt(value.substring(5,7));
        int startDate= Integer.parseInt(value.substring(8,10));

        Calendar arrDate = Calendar.getInstance();
        arrDate.set(startYear, startMonth -1, startDate);

        Boolean bValid = false;
        if (Start.before(arrDate) && End.after(arrDate)) {
            bValid = true;
        }
        return bValid;
    }




///////////검색 관련 메소드/////////////////
    // 검색을 수행하는 메소드
    public void search(String charText) {
        // 리스트의 모든 데이터를 검색한다.
        for (int i = 0; i < arr.size(); i++) {
            // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
            if (arr.get(i).getTitle().contains(charText) || arr.get(i).getContent().contains(charText)) {
                //해쉬로 검색된 데이터를 리스트에 추가한다.
                insertList.add(arr.get(i));
            }
        }
    }

    public void delete(String charText) {
        // 리스트의 모든 데이터를 검색한다.
        for (int i = 0; i < arr.size(); i++) {
            // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
            if (!(arr.get(i).getTitle().contains(charText) || arr.get(i).getContent().contains(charText))) {
                //해쉬로 검색된 데이터를 리스트에 삭제한다.
                insertList.remove(arr.get(i));
            }
        }
    }

    public void convert() {
        //변환
        searchList = new ArrayList<DiaryVO>(insertList);
        Collections.sort(searchList, new SortByDate());
        Collections.reverse(searchList);

        adapter = new MyAdapter(getActivity(), R.layout.list_form, searchList, searchListView);
        //준비된 어댑터를 ListView에 추가
        searchListView.setAdapter(adapter);
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        //adapter.notifyDataSetChanged();
    }


}