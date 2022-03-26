package com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet;

public class ThoughJetApi {

    public record ToughJetRequest(String from, String to, String outboundDate,
                                  String inboundDate, int numberOfAdults) {
    }

    public record ToughJetResponse(String carrier, double basePrice, double tax, double discount,
                                   String departureAirportName, String arrivalAirportName,
                                   String outboundDateTime, String inboundDateTime) {
    }

}
