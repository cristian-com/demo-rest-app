package com.travix.medusa.busyflights.domain.busyflights.services;

import com.travix.medusa.busyflights.buildingblocks.queries.Query;
import com.travix.medusa.busyflights.buildingblocks.queries.QueryHandler;
import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightQueryHandler implements QueryHandler<FlightQueryHandler.FlightQuery, List<Flight>> {

    private final List<FlightSupplierGateway> flightSuppliers;

    @Override
    public List<Flight> handle(FlightQuery query) {
        final FlightSearch flightSearch = FlightSearch.builder()
                .id(query.id)
                .origin(query.origin)
                .destination(query.destination)
                .departureDate(query.departureDate)
                .returnDate(query.returnDate)
                .passengers(query.numberOfPassenger)
                .build();

        List<Flight> flights = flightSuppliers.parallelStream()
                .map(supplier -> supplier.query(flightSearch))
                .flatMap(List::stream)
                .toList();

        return flights.stream()
                .sorted(Comparator.comparing(Flight::fare))
                .toList();
    }

    public record FlightQuery(Serializable id,
                              String origin,
                              String destination,
                              LocalDate departureDate,
                              LocalDate returnDate,
                              int numberOfPassenger) implements Query {
    }

}
