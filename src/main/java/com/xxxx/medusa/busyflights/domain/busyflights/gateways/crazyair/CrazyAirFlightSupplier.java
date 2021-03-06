package com.xxxx.medusa.busyflights.domain.busyflights.gateways.crazyair;

import com.xxxx.medusa.busyflights.buildingblocks.Adapter;
import com.xxxx.medusa.busyflights.buildingblocks.utils.Dates;
import com.xxxx.medusa.busyflights.domain.busyflights.Flight;
import com.xxxx.medusa.busyflights.domain.busyflights.FlightSearch;
import com.xxxx.medusa.busyflights.domain.busyflights.FlightSupplier;
import com.xxxx.medusa.busyflights.domain.busyflights.services.FlightSupplierGateway;
import com.xxxx.medusa.busyflights.domain.busyflights.IATACode;
import com.xxxx.medusa.busyflights.domain.busyflights.TimePeriod;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class CrazyAirFlightSupplier implements FlightSupplierGateway {

    private final CrazyFlightGetFlightsResponseAdapter adapter;
    private final CrazyAirApi crazyAirApi;

    public CrazyAirFlightSupplier(CrazyAirApi crazyAirApi) {
        this.crazyAirApi = Objects.requireNonNull(crazyAirApi);
        this.adapter = new CrazyFlightGetFlightsResponseAdapter();
    }

    @Override
    public List<Flight> query(@NotNull FlightSearch flightSearch) {
        Objects.requireNonNull(flightSearch);

        var request = new CrazyAirApi.CrazyAirRequest(
                flightSearch.getOrigin().getTextCode(),
                flightSearch.getDestination().getTextCode(),
                Dates.isoLocalDate(flightSearch.getTimePeriod().getDepartureTime()),
                Dates.isoLocalDate(flightSearch.getTimePeriod().getReturnTime()),
                flightSearch.getPassengers().getNumber());

        List<CrazyAirApi.CrazyAirResponse> responses = crazyAirApi.get().exchange(request);

        return adapter.adapt(responses);
    }

    private static class CrazyFlightGetFlightsResponseAdapter implements
            Adapter<CrazyAirApi.CrazyAirResponse, Flight> {

        @Override
        public Flight adapt(CrazyAirApi.CrazyAirResponse response) {
            return new Flight(
                    response.airline(),
                    FlightSupplier.CRAZY_AIR,
                    BigDecimal.valueOf(response.price()),
                    new IATACode(response.departureAirportCode()),
                    new IATACode(response.destinationAirportCode()),
                    new TimePeriod(Dates.isoLocalDateTime(response.departureDate()),
                            Dates.isoLocalDateTime(response.arrivalDate()))
            );
        }
    }

}
