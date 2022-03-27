package com.travix.medusa.busyflights.buildingblocks.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Null safe functions
 */
public final class Dates {

    private Dates() {
    }

    public static String isoLocalDate(LocalDateTime value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return value.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String isoLocalDate(LocalDate value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return value.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static LocalDateTime isoLocalDateTime(String value, DateTimeFormatter formatter) {
        if (Objects.isNull(value)) {
            return null;
        }

        return LocalDateTime.parse(value, formatter);
    }

    public static LocalDateTime isoLocalDateTime(String value) {
        return isoLocalDateTime(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static LocalDate isoLocalDate(String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
