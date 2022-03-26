package com.travix.medusa.busyflights.domain.busyflights.services;

import com.travix.medusa.busyflights.buildingblocks.Query;
import com.travix.medusa.busyflights.buildingblocks.QueryHandler;
import com.travix.medusa.busyflights.domain.busyflights.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightQueryHandler implements QueryHandler<FlightQueryHandler.FlightQuery, List<Flight>> {

    private final List<FlightSupplierGateway> flightSuppliers;

    @Override
    public List<Flight> handle(FlightQuery query) {
        return null;
    }

    public record FlightQuery(Serializable id,
                              String origin,
                              String destination,
                              String departureDate,
                              String returnDate,
                              int numberOfPassenger) implements Query {
    }

}
