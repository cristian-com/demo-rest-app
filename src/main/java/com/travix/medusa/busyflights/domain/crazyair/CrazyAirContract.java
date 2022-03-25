package com.travix.medusa.busyflights.domain.crazyair;

public final class CrazyAirContract {

    public record CrazyAirRequest(String origin, String destination, String departureDate,
                                  String returnDate, int passengerCount) {
    }

    public record CrazyAirResponse(String airline, double price, String cabinclass,
                                   String departureAirportCode, String destinationAirportCode,
                                   String departureDate, String arrivalDate) {
    }

}
