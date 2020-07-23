package com.huawei.ist.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferenceHelper {
    private final static String PREF_NAME = "wallet";
    public static void setSharedPreferenceObject(Context context, String key, Object obj) {
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(key, json);
        editor.commit();
    }
    public static String getSharedPreferenceObject(Context context, String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, 0);
        Gson gson = new Gson();
        return settings.getString(key, defValue);
    }
}
