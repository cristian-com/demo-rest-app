package com.travix.medusa.busyflights.domain.busyflights.services;

import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface FlightSupplierGateway {

    @NotNull List<Flight> query(FlightSearch flightSearch);

}
