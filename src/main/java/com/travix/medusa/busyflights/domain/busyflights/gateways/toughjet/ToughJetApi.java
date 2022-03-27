package com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet;

import com.travix.medusa.busyflights.buildingblocks.ExchangeTrait;

import java.util.List;

public class ToughJetApi {

    public record ToughJetRequest(String from, String to, String outboundDate,
                                  String inboundDate, int numberOfAdults) {
    }

    public record ToughJetResponse(String carrier, double basePrice, double tax, double discount,
                                   String departureAirportName, String arrivalAirportName,
                                   String outboundDateTime, String inboundDateTime) {
    }

    private final ToughJetApi.GET get = new ToughJetApi.GET();

    public ToughJetApi.GET get() {
        return get;
    }

    public static class GET implements ExchangeTrait<List<ToughJetApi.ToughJetResponse>, ToughJetApi.ToughJetRequest> {
        @Override
        public List<ToughJetApi.ToughJetResponse> exchange(ToughJetApi.ToughJetRequest thoughJetRequest) {
            return null;
        }
    }

}
