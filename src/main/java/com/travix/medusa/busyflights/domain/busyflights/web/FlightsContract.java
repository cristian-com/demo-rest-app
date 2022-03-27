package com.travix.medusa.busyflights.domain.busyflights.web;

import java.time.LocalDate;

public final class FlightsContract {

    private FlightsContract() {
    }

    public record Request(String origin,
                          String destination,
                          LocalDate departureDate,
                          LocalDate returnDate,
                          int numberOfPassengers) {
    }

    public record BusyFlightsResponse() {
    }

}
