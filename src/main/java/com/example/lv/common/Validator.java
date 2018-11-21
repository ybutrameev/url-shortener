package com.example.lv.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {
    public static final Validator INSTANCE = new Validator();
    private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
    private static final String CUSTOM_ID_REGEX = "^[A-Za-z0-9]+$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    private static final Pattern CUSTOM_ID_PATTERN = Pattern.compile(CUSTOM_ID_REGEX);

    private Validator() {
    }

    public boolean isUrlValid(String url) {
        Matcher m = URL_PATTERN.matcher(url);
        return m.matches();
    }

    public boolean isIdValid(String customId) {
        Matcher m = CUSTOM_ID_PATTERN.matcher(customId);
        return m.matches() && customId.length() <= 12;
    }
}
