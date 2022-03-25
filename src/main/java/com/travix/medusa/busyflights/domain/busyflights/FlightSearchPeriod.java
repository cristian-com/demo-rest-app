package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.buildingblocks.ArgumentAssertions;
import com.travix.medusa.buildingblocks.ValueObject;
import com.travix.medusa.buildingblocks.VisibleForTesting;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@EqualsAndHashCode
public class FlightSearchPeriod implements ValueObject {

    @VisibleForTesting
    protected static final String DEPARTURE_TIME = "departure time";
    protected static final String DEPARTURE_DATE = "departure date";
    protected static final String RETURN_TIME = "return time";
    protected static final String RETURN_DATE = "return date";

    private final LocalDateTime departureTime;
    private final LocalDateTime returnTime;

    public FlightSearchPeriod(LocalDateTime departureTime, LocalDateTime returnTime) {
        this.departureTime = ArgumentAssertions.assertNonNull(DEPARTURE_TIME, departureTime);

        if (returnTime == null) {
            this.returnTime = null;
        } else {
            this.returnTime = ArgumentAssertions.assertInTheFuture(DEPARTURE_TIME, departureTime, RETURN_TIME, returnTime);
        }
    }

    public static FlightSearchPeriod of(LocalDate departureDate, LocalDate returnDate) {
        ArgumentAssertions.assertNonNull(DEPARTURE_DATE, departureDate);

        LocalDateTime from = departureDate.atStartOfDay();
        LocalDateTime to = returnDate == null ? null : LocalDateTime.of(returnDate, LocalTime.MAX);

        return new FlightSearchPeriod(from, to);
    }

}
