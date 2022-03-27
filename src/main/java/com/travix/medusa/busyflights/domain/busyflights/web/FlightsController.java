package com.travix.medusa.busyflights.domain.busyflights.web;

import com.travix.medusa.busyflights.buildingblocks.queries.QueryDispatcher;
import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.services.FlightQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "flights", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class FlightsController {

    private final QueryDispatcher dispatcher;
    private final FlightQueryHandler flightQueryHandler;

    @GetMapping
    public ResponseEntity<List<Flight>> list(@Valid FlightsRestContract.Request request) {
        var query = new FlightQueryHandler.FlightQuery(
                UUID.randomUUID(),
                request.origin(),
                request.destination(),
                request.departureDate(),
                request.returnDate(),
                request.numberOfPassengers()
        );

        return ResponseEntity.ok(dispatcher.dispatch(flightQueryHandler, query));
    }

}
