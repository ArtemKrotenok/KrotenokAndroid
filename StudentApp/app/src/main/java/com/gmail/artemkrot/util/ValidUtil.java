package com.gmail.artemkrot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {
    private static final String PATTERN_REGEX_IMAGE_URL = "^http.+|^www.+";

    public static boolean isValidUrl(String url) {
        Pattern pattern = Pattern.compile(PATTERN_REGEX_IMAGE_URL);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}