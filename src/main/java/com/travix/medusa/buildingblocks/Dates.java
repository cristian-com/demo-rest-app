package com.travix.medusa.buildingblocks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Dates {

    private Dates() {}

    public static LocalDateTime iso(String value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
