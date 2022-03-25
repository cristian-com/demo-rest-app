package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.buildingblocks.ArgumentAssertions;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This class is in charge of handling invariants and builders that make sense.
 * <p>
 * This would be a good candidate for an "Entity", let's say a "non-persistent" one
 */
@Data
@EqualsAndHashCode(of = "id")
public class FlightSearch {

    protected static final String ORIGIN = "origin";
    protected static final String DESTINATION = "destination";
    protected static final String PERIOD = "period";
    protected static final String PASSENGERS = "passengers";

    // I'm assigning this value internally, but an external provider would be preferable
    private final Serializable id;
    private IATACode origin;
    private IATACode destination;
    private FlightSearchPeriod flightSearchPeriod;
    private PassengersNumber passengers;

    @Builder
    // Main constructor, All args
    public FlightSearch(IATACode origin, IATACode destination, FlightSearchPeriod flightSearchPeriod,
                        PassengersNumber passengers) {
        ArgumentAssertions.assertNonNull(ORIGIN, origin);
        ArgumentAssertions.assertNonNull(DESTINATION, destination);

        id = UUID.randomUUID();
        this.origin = ArgumentAssertions.assertNonEquals(DESTINATION, destination, ORIGIN, origin);
        this.destination = destination;
        this.flightSearchPeriod = flightSearchPeriod == null ? FlightSearchPeriod.of(LocalDate.now()) : flightSearchPeriod;
        this.passengers = passengers == null ? PassengersNumber.min() : passengers;
    }

    @Builder(builderMethodName = "plainBuilder")
    public FlightSearch(String origin, String destination, LocalDate from, LocalDate to, Integer passengers) {
        this(new IATACode(origin), new IATACode(destination), FlightSearchPeriod.of(from, to), new PassengersNumber(passengers));
    }

}
