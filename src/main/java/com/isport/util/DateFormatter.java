package com.isport.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DAY_OF_MONTH;

public class DateFormatter {
    public static String formatDate(Date date) {
        String[] suffixes = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                "th", "th", "th", "th", "th", "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "st"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d");
        int day = Calendar.getInstance().get(DAY_OF_MONTH);
        return simpleDateFormat.format(date) + suffixes[day];
    }
}
