package com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet;

import com.travix.medusa.busyflights.buildingblocks.Adapter;
import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch;
import com.travix.medusa.busyflights.domain.busyflights.services.FlightSupplierGateway;

import java.util.List;
import java.util.Objects;

public class ThoughJetFlightSupplier implements FlightSupplierGateway  {

    private final ThoughJetFlightSupplier.ThoughJetGetFlightsResponseAdapter adapter;
    private final ThoughJetApi thoughJetApi;

    public ThoughJetFlightSupplier(ThoughJetApi thoughJetApi) {
        this.thoughJetApi = Objects.requireNonNull(thoughJetApi);
        this.adapter = new ThoughJetFlightSupplier.ThoughJetGetFlightsResponseAdapter();
    }

    @Override
    public List<Flight> query(FlightSearch flightSearch) {
        Objects.requireNonNull(flightSearch);

        return adapter.adapt((List<ThoughJetApi.ToughJetResponse>) null);
    }

    private static class ThoughJetGetFlightsResponseAdapter implements
            Adapter<ThoughJetApi.ToughJetResponse, Flight> {

        @Override
        public Flight adapt(ThoughJetApi.ToughJetResponse response) {
            return null;
        }
    }

}
