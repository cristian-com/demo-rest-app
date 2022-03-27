package com.travix.medusa.busyflights.buildingblocks.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public static String isoDateTime(LocalDateTime value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return value.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static LocalDateTime isoLocalDateTime(String value, DateTimeFormatter formatter)
            throws IllegalArgumentException {
        Objects.requireNonNull(formatter);

        if (Objects.isNull(value)) {
            return null;
        }

        // This could be a switch to handle more formats
        if (DateTimeFormatter.ISO_INSTANT.equals(formatter)) {
            return LocalDateTime.ofInstant(Instant.parse(value), ZoneId.systemDefault());
        } else {
            throw new IllegalArgumentException("LocalDateTime unsupported format: " + formatter);
        }
    }

    public static LocalDateTime isoLocalDateTime(String value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static LocalDate isoLocalDate(String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
