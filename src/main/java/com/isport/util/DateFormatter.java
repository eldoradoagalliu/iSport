package com.isport.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static java.util.Calendar.DAY_OF_MONTH;

public class DateFormatter {
    public static String formatDateWithSuffix(Date date) {
        String[] suffixes = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                "th", "th", "th", "th", "th", "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "st"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d");
        int day = Calendar.getInstance().get(DAY_OF_MONTH);
        return simpleDateFormat.format(date) + suffixes[day];
    }

    public static Date getDateFromString(String date, String time) {
        String dateTimeString = date + " " + time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date parsedDate = null;

        if (Objects.nonNull(date) && Objects.nonNull(time) && !dateTimeString.equals(date + " ") && !dateTimeString.equals(" " + time)) {
            try {
                parsedDate = format.parse(dateTimeString);
                return new Date(parsedDate.getTime());
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        return parsedDate;
    }
}
