package com.xxxx.medusa.busyflights.domain.busyflights.services;

import com.xxxx.medusa.busyflights.domain.busyflights.Flight;
import com.xxxx.medusa.busyflights.domain.busyflights.FlightSearch;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface FlightSupplierGateway {

    @NotNull List<Flight> query(@NotNull FlightSearch flightSearch);

}
