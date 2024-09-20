package com.nmt.groceryfinder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/25/2024
 */
public class DefaultValueUtil {
    private static final String DEFAULT_DATE_STRING = "30/04/1975";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Returns the default date (1/1/1975) as a Date object.
     * @return Date object representing the default date.
     */
    public static Date getDefaultBirthday() {
        try {
            return DATE_FORMAT.parse(DEFAULT_DATE_STRING);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse default date", e);
        }
    }
}
