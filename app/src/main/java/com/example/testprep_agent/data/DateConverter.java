package com.example.testprep_agent.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    public static String fromDate(Date value) {
        if (value == null) {
            return null;
        } else {
            return FORMATTER.format(value);
        }
    }

    public static Date toDate(String value) {
        try {
            return FORMATTER.parse(value);
        } catch (ParseException ex) {
            return null;
        }
    }
}
