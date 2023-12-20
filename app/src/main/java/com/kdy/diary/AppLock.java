package com.kdy.diary;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public final class AppLock{
    SharedPreferences pref;

    public AppLock(Context context){
        this.pref = context.getSharedPreferences("applock", MODE_PRIVATE);
    }

    //잠금 비밀번호 저장
    public void setPassLock(String password){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("applock", password);
        edit.commit();
    }

    //잠금 설정 제거
    public void removePassLock(){
        SharedPreferences.Editor edit = pref.edit();
        edit.remove("applock");
        edit.commit();
    }

    //비밀번호 체크
    public Boolean checkPassLock(String password){
        return pref.getString("applock","0").equals(password);
    }

    //잠금 설정 여부
    public Boolean isPassLockSet(){
        if(pref.contains("applock")){
            return true;
        }
        return false;
    }
}
