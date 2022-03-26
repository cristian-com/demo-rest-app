package com.travix.medusa.busyflights.domain.busyflights.gateways.crazyair;

import com.travix.medusa.buildingblocks.Adapter;
import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch;
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplierGateway;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CrazyAirFlightSupplier implements FlightSupplierGateway  {

    private final CrazyFlightGetFlightsResponseAdapter adapter = new CrazyFlightGetFlightsResponseAdapter();
    private final CrazyAirApi crazyAirApi;

    public CrazyAirFlightSupplier(CrazyAirApi crazyAirApi) {
        this.crazyAirApi = crazyAirApi;
    }

    @Override
    public List<Flight> query(@NotNull FlightSearch flightSearch) {
        Objects.requireNonNull(flightSearch);

        var request = new CrazyAirApi.CrazyAirRequest(
                flightSearch.getOrigin().getTextCode(),
                flightSearch.getDestination().getTextCode(),
                null, null,
                flightSearch.getPassengers().getNumber());

        List<CrazyAirApi.CrazyAirResponse> responses = crazyAirApi.get().exchange(request);

        return adapter.adapt(responses);
    }

    private static class CrazyFlightGetFlightsResponseAdapter implements
            Adapter<CrazyAirApi.CrazyAirResponse, Flight> {

        @Override
        public Flight adapt(CrazyAirApi.CrazyAirResponse response) {
            return null;
        }

    }

}
