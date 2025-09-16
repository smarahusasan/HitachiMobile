package com.glo.utils;

import java.util.regex.Pattern;

public class AppConstants {
    public final static Pattern EMAIL_REGEX= Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[A-Za-z]{2,3}$");
    public final static Pattern NAME_REGEX= Pattern.compile("^[A-Za-z]{1,15}$");
    public final static Pattern CITY_STATE_REGEX= Pattern.compile("^[A-Za-z]+$");
}
