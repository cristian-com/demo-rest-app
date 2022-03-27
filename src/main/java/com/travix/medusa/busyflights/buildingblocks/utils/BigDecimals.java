package com.travix.medusa.busyflights.buildingblocks.utils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public final class BigDecimals {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private BigDecimals() {}

    public static BigDecimal percentage(@NotNull BigDecimal base, @NotNull BigDecimal pct) {
        Objects.requireNonNull(base);
        Objects.requireNonNull(pct);

        return base.multiply(pct).divide(ONE_HUNDRED);
    }

}
