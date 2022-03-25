package com.travix.medusa.busyflights.domain.busyflights

import com.travix.medusa.buildingblocks.Dates
import spock.lang.Specification

import java.time.LocalDateTime

import static com.travix.medusa.buildingblocks.ArgumentAssertions.LAST_DATE_CAN_NOT_BE_IN_THE_PAST
import static com.travix.medusa.buildingblocks.ArgumentAssertions.NULL_MESSAGE
import static com.travix.medusa.buildingblocks.ArgumentAssertions.getErrorMessage
import static com.travix.medusa.busyflights.domain.busyflights.FlightSearchPeriod.DEPARTURE_TIME
import static com.travix.medusa.busyflights.domain.busyflights.FlightSearchPeriod.RETURN_TIME

/**
 * Specification for flight search object
 */
class FlightSearchSpec extends Specification {

    // ************************* Search Period ************************* \\

    def "Search period must have a departure time"() {
        when:
        new FlightSearchPeriod(null, LocalDateTime.now())

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(NULL_MESSAGE, DEPARTURE_TIME)
    }

    def "Search period doesn't need a return"() {
        when:
        new FlightSearchPeriod(LocalDateTime.now(), null)

        then:
        noExceptionThrown()
    }

    def "Return time should be in the future when comparing with departure time"() {
        when:
        new FlightSearchPeriod(from, to)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(LAST_DATE_CAN_NOT_BE_IN_THE_PAST, RETURN_TIME, DEPARTURE_TIME)

        where:
        from                             | to
        Dates.iso("2011-12-03T10:15:30") | Dates.iso("2011-12-03T10:15:30") // Same date
        Dates.iso("2011-12-03T10:15:31") | Dates.iso("2011-12-03T10:15:30") // Date on the past
    }

}
