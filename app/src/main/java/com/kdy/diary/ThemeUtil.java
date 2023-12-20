package com.kdy.diary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtil {
    public static final String LIGHT_MODE = "light";
    public static final String DARK_MODE = "dark";
    public static final String DEFAULT_MODE = "default";

    public static final String TAG = "ThemeUtil";

    public static void applyTheme(String themeColor) {
        switch (themeColor) {
            case LIGHT_MODE:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Log.d(TAG,"라이트 모드 적용됨");
                break;

            case DARK_MODE:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Log.d(TAG,"다크 모드 적용됨");
                break;

            // 안드로이드 10버전 이상 혹은 이하에는 서로 다른 디폴트 모드를 적용해야 된다.

            default:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    Log.d(TAG, "안드로이드 10 이상 디폴트 모드가 적용됨");
                }else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                    Log.d(TAG, "안드로이드 10 이하 디폴트 모드가 적용됨");
                    break;
        }
    }

    // 앱이 종료되도 지정한 테마가 저장될 수 있게 저장하는 메서드
    public static void modSave(Context context, String select_mod){
        SharedPreferences sp;
        sp = context.getSharedPreferences("mod" , context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("mod", select_mod);
        editor.commit();
    }

    // 앱이 종료되도 저장된 테마를 불러올 수 있게하는 메서드
    public static String modLoad(Context context){
        SharedPreferences sp;
        sp = context.getSharedPreferences("mod" , context.MODE_PRIVATE);
        String load_mod = sp.getString("mod","light");
        return load_mod;
    }

}
