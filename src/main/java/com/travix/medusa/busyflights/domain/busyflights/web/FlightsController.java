package com.travix.medusa.busyflights.domain.busyflights.web;

import com.travix.medusa.busyflights.buildingblocks.Adapter;
import com.travix.medusa.busyflights.buildingblocks.queries.QueryDispatcher;
import com.travix.medusa.busyflights.buildingblocks.utils.Dates;
import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch;
import com.travix.medusa.busyflights.domain.busyflights.services.FlightQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(value = "flights", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FlightsController {

    private final QueryDispatcher dispatcher;
    private final FlightQueryHandler flightQueryHandler;
    private final ResponseAdapter responseAdapter = new ResponseAdapter();

    @GetMapping
    public ResponseEntity<FlightsRestContract.Response> list(@Valid FlightsRestContract.Request request) {
        var query = new FlightQueryHandler.FlightQuery(
                UUID.randomUUID(),
                request.origin(),
                request.destination(),
                request.departureDate(),
                request.returnDate(),
                request.numberOfPassengers()
        );

        FlightSearch result = dispatcher.dispatch(flightQueryHandler, query);

        // This is the last bit I'm working on, so I won't be able to implement it as I would like
        // Concerns like results ordering, limits, formats. Should be ideally implemented consistently across public interfaces
        // All those concerns should have their own objects with well-defined responsibilities
        return ResponseEntity.ok(responseAdapter.adapt(result));
    }

    public static class ResponseAdapter implements Adapter<FlightSearch, FlightsRestContract.Response> {

        @Override
        public FlightsRestContract.Response adapt(FlightSearch search) {
            return new FlightsRestContract.Response(
                    new FlightsRestContract.AirportOptions(
                            search.getOrigin().getTextCode(),
                            getOptions(search.getResults().getOutwardOptions())
                    ),
                    new FlightsRestContract.AirportOptions(
                            search.getDestination().getTextCode(),
                            getOptions(search.getResults().getReturnOptions())
                    ));
        }

        private List<FlightsRestContract.FlightOption> getOptions(List<Flight> flightList) {
            return flightList.stream().sorted(Comparator.comparing(Flight::fare)).map(this::getOption).toList();
        }

        private FlightsRestContract.FlightOption getOption(Flight flight) {
            return new FlightsRestContract.FlightOption(
                    flight.airline(),
                    flight.supplier().toString(),
                    flight.fare().setScale(2, RoundingMode.HALF_UP).toString(),
                    Dates.isoDateTime(flight.timePeriod().getDepartureTime()),
                    Dates.isoDateTime(flight.timePeriod().getDepartureTime())
            );
        }

    }

}
