package com.travix.medusa.busyflights.domain.busyflights.web;

import com.travix.medusa.busyflights.buildingblocks.QueryDispatcher;
import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.services.FlightQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("flights")
@RequiredArgsConstructor
public class FlightsController {

    private final QueryDispatcher dispatcher;
    private final FlightQueryHandler flightQueryHandler;

    @GetMapping
    public void list(@RequestBody FlightsContract.Request request) {
        var query = new FlightQueryHandler.FlightQuery(
                UUID.randomUUID(),
                request.origin(),
                request.destination(),
                request.departureDate(),
                request.returnDate(),
                request.numberOfPassengers()
        );

        List<Flight> response = dispatcher.dispatch(flightQueryHandler, query);
    }

}
