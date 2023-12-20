package com.kdy.diary;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) { super(fm);}


    @NonNull
    @Override
    public Fragment getItem(int position) {

        //각 페이지별로 보여줄 Fragment를 설정하는 메서드

        switch(position) {
            case 0:
                return new CalendarFragment();
            case 1:
                return new ListFragment();
        }//switch

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
