package com.example.kamusbali;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Global {

    public static class Constant {

        public static class State {
            public final static String DIC_TYPE = "DIC_TYPE";
        }

    }

    public static void saveState(Activity activity, String key, int value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getState(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String string = sharedPref.getString(key, null);
        return string;
    }

    public static void putDicType(Activity activity, int value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Constant.State.DIC_TYPE, value);
        editor.apply();
    }

    public static int getDicType(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        int dicType = sharedPref.getInt(Constant.State.DIC_TYPE, 0);
        return dicType;
    }
}