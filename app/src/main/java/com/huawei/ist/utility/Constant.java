package com.huawei.ist.utility;

import android.content.Context;
import android.graphics.Typeface;

import java.util.List;

public class Constant {

    public static Typeface getTypeface(Context context,int fontType){
        Typeface typeface = null;
        if (fontType ==1){
            typeface = Typeface.createFromAsset(context.getAssets(),"fonts/shablagooital.ttf");
        }
        return typeface;
    }

}
