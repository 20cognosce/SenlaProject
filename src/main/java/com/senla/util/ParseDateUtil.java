package com.senla.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ParseDateUtil {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.dateFormat);

    static public LocalDate getDateFromString(String date) {
        if (Objects.isNull(date)) {
            return LocalDate.now();
        } else {
            return LocalDate.parse(date, dtf);
        }
    }
}
