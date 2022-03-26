package com.travix.medusa.busyflights.domain.busyflights.services;

import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch;

import java.util.List;

public interface FlightSupplierGateway {

    List<Flight> query(FlightSearch flightSearch);

}
