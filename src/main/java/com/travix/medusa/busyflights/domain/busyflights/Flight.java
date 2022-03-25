package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.buildingblocks.ValueObject;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Flight implements ValueObject {

    private final String airline;
    private final FlightSupplier supplier;
    private final BigDecimal fare;
    private final IATACode departure;
    private final IATACode destination;
    private final Period period;

    public Flight(String airline, FlightSupplier supplier, BigDecimal fare,
                  IATACode departure, IATACode destination, Period period) {
        this.airline = airline;
        this.supplier = supplier;
        this.fare = fare;
        this.departure = departure;
        this.destination = destination;
        this.period = period;
    }

}
