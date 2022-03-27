package com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet;

import com.travix.medusa.busyflights.buildingblocks.ExchangeTrait;

import java.util.List;

public class ThoughJetApi {

    public record ToughJetRequest(String from, String to, String outboundDate,
                                  String inboundDate, int numberOfAdults) {
    }

    public record ToughJetResponse(String carrier, double basePrice, double tax, double discount,
                                   String departureAirportName, String arrivalAirportName,
                                   String outboundDateTime, String inboundDateTime) {
    }

    private final ThoughJetApi.GET get = new ThoughJetApi.GET();

    public ThoughJetApi.GET get() {
        return get;
    }

    public static class GET implements ExchangeTrait<List<ThoughJetApi.ToughJetResponse>, ThoughJetApi.ToughJetRequest> {
        @Override
        public List<ThoughJetApi.ToughJetResponse> exchange(ThoughJetApi.ToughJetRequest thoughJetRequest) {
            return null;
        }
    }

}
