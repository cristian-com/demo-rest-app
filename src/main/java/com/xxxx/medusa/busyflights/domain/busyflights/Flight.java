package com.xxxx.medusa.busyflights.domain.busyflights;

import com.xxxx.medusa.busyflights.buildingblocks.ValueObject;

import java.math.BigDecimal;

public record Flight(String airline,
                     FlightSupplier supplier,
                     BigDecimal fare,
                     IATACode departure,
                     IATACode destination,
                     TimePeriod timePeriod) implements ValueObject {
}
