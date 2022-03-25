package com.travix.medusa.busyflights.domain.busyflights;

import java.util.List;

public interface FlightSupplierGateway {

    List<Flight> query(FlightSearch flightSearch);

}
