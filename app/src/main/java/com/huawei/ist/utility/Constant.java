package com.huawei.ist.utility;

import android.content.Context;
import android.graphics.Typeface;

import java.util.List;

public class Constant {

    public static String API_KEY = "YOUR_API_KEY";
    public static final String PUBLIC_KEY =  "YOUR_PUBLIC_KEY";
    public static final String PRIVATE_KEY = "YOUR_PRIVATE_KEY";
    public static final String  BASE_URL = "http://YOUR_IPV4_ADDERESS:PORT";
    public static Typeface getTypeface(Context context,int fontType){
        Typeface typeface = null;
        if (fontType == 1){
            typeface = Typeface.createFromAsset(context.getAssets(),"fonts/shablagooital.ttf");
        }else{
            typeface = Typeface.createFromAsset(context.getAssets(),"fonts/TitilliumWeb-Bold.ttf");
        }
        return typeface;
    }





}
