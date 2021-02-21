package com.gmail.artemkrot.coronavirus.ui;

import android.icu.text.DecimalFormat;

public class TextFormatUtil {
    public static String formattedNumber(Long number) {
        return new DecimalFormat("###,###").format(number);
    }
}