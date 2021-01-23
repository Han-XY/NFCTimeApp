package com.txbb.nfctimeapp.util;

import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Units {

    public static int dpConvert(int dp, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                displayMetrics
        );
    }

}
