package com.travix.medusa.busyflights.domain.busyflights.web;

public final class FlightsContract {

    private FlightsContract() {
    }

    public record Request(String origin,
                          String destination,
                          String departureDate,
                          String returnDate,
                          int numberOfPassengers) {
    }

    public record BusyFlightsResponse() {
    }

}
