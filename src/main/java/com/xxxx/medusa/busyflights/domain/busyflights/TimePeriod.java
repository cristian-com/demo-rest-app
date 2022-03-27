package com.xxxx.medusa.busyflights.domain.busyflights;

import com.xxxx.medusa.busyflights.buildingblocks.utils.ArgumentAssertions;
import com.xxxx.medusa.busyflights.buildingblocks.ValueObject;
import com.xxxx.medusa.busyflights.buildingblocks.VisibleForTesting;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@ToString
@EqualsAndHashCode
public class TimePeriod implements ValueObject {

    @VisibleForTesting
    protected static final String DEPARTURE_TIME = "departure time";
    protected static final String DEPARTURE_DATE = "departure date";
    protected static final String RETURN_TIME = "return time";
    protected static final String RETURN_DATE = "return date";

    private final LocalDateTime departureTime;
    private final LocalDateTime returnTime;

    public TimePeriod(LocalDateTime departureTime, LocalDateTime returnTime) {
        this.departureTime = ArgumentAssertions.assertNonNull(DEPARTURE_TIME, departureTime);

        if (returnTime == null) {
            this.returnTime = null;
        } else {
            this.returnTime = ArgumentAssertions.assertInTheFuture(DEPARTURE_TIME, departureTime, RETURN_TIME, returnTime);
        }
    }

    public static TimePeriod of(LocalDate departureDate) {
        return of(departureDate, null);
    }

    public static TimePeriod of(LocalDate departureDate, LocalDate returnDate) {
        ArgumentAssertions.assertNonNull(DEPARTURE_DATE, departureDate);

        LocalDateTime from = departureDate.atStartOfDay();
        LocalDateTime to = returnDate == null ? null : LocalDateTime.of(returnDate, LocalTime.MAX);

        return new TimePeriod(from, to);
    }

}
