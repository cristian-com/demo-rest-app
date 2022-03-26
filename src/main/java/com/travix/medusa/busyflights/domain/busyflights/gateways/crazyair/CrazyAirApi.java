package com.travix.medusa.busyflights.domain.busyflights.gateways.crazyair;

import com.travix.medusa.buildingblocks.ExchangeTrait;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrazyAirApi {

    public record CrazyAirRequest(String origin, String destination, String departureDate,
                                  String returnDate, int passengerCount) {
    }

    public record CrazyAirResponse(String airline, double price, String cabinclass,
                                   String departureAirportCode, String destinationAirportCode,
                                   String departureDate, String arrivalDate) {
    }

    private final GET get = new GET();

    public GET get() {
        return get;
    }

    public static class GET implements ExchangeTrait<List<CrazyAirResponse>, CrazyAirRequest> {
        @Override
        public List<CrazyAirResponse> exchange(CrazyAirRequest crazyAirRequest) {
            return null;
        }
    }

}
