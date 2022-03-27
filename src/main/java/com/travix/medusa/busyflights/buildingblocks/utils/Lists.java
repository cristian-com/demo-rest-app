package com.travix.medusa.busyflights.buildingblocks.utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Lists {

    private Lists() {}

    public static <T> List<T> unmodifiable(List<T> flights) {
        if (Objects.isNull(flights)) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(flights);
        }
    }

}
