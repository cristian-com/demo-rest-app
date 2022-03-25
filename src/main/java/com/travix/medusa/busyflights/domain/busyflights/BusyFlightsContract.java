package com.travix.medusa.busyflights.domain.busyflights;

public final class BusyFlightsContract {

    private BusyFlightsContract() {
    }

    public record BusyFlightsRequest(String origin, String destination, String departureDate,
                                     String returnDate, int numberOfPassengers) {
    }

    public record BusyFlightsResponse() {
    }

}
