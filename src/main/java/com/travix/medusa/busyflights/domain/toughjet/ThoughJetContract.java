package com.travix.medusa.busyflights.domain.toughjet;

public class ThoughJetContract {

    public record ToughJetRequest(String from, String to, String outboundDate,
                                  String inboundDate, int numberOfAdults) {
    }

    public record ToughJetResponse(String carrier, double basePrice, double tax, double discount,
                                   String departureAirportName, String arrivalAirportName,
                                   String outboundDateTime, String inboundDateTime) {
    }

}
