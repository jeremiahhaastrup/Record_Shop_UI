package com.northcoders.recordshopapplication.util;

import androidx.databinding.InverseMethod;

public class Converter {
    @InverseMethod("stringToInt")
    public static String intToString(int value) {
        if (value == 0) {
            return "";
        }
        return String.valueOf(value);
    }

    public static int stringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @InverseMethod("stringToIntDisplay")
    public static String intToStringDisplay(int value) {
        return String.valueOf(value);
    }

    public static int stringToIntDisplay(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}