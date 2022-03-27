package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.busyflights.buildingblocks.utils.ArgumentAssertions;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "id")
public class FlightSearch {

    protected static final String ORIGIN = "origin";
    protected static final String DESTINATION = "destination";
    protected static final String PERIOD = "period";
    protected static final String PASSENGERS = "passengers";

    private final Serializable id;
    private IATACode origin;
    private IATACode destination;
    private TimePeriod timePeriod;
    private PassengersNumber passengers;

    // Main constructor, All args
    public FlightSearch(Serializable id, IATACode origin, IATACode destination, TimePeriod timePeriod,
                        PassengersNumber passengers) {
        ArgumentAssertions.assertNonNull(ORIGIN, origin);
        ArgumentAssertions.assertNonNull(DESTINATION, destination);

        this.id = ArgumentAssertions.assertNonNull("id", id);
        this.origin = ArgumentAssertions.assertNonEquals(DESTINATION, destination, ORIGIN, origin);
        this.destination = destination;
        // Time period starts from now() and not end time by default
        this.timePeriod = timePeriod == null ? TimePeriod.of(LocalDate.now()) : timePeriod;
        this.passengers = passengers == null ? PassengersNumber.min() : passengers;
    }

    @Builder
    public FlightSearch(Serializable id, String origin, String destination, LocalDate departureDate,
                        LocalDate returnDate, int passengers) {
        this(id, new IATACode(origin), new IATACode(destination), TimePeriod.of(departureDate, returnDate), new PassengersNumber(passengers));
    }

}
