package com.ly.demo2.util;

import android.content.Context;

public class CommonUtil {


    /**
     * sp转px的方法。
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
