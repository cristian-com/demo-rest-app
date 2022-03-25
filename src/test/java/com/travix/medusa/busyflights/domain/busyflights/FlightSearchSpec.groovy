package com.travix.medusa.busyflights.domain.busyflights

import com.travix.medusa.buildingblocks.Dates
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import static com.travix.medusa.buildingblocks.ArgumentAssertions.LAST_DATE_CAN_NOT_BE_IN_THE_PAST
import static com.travix.medusa.buildingblocks.ArgumentAssertions.MUST_BE_BETWEEN
import static com.travix.medusa.buildingblocks.ArgumentAssertions.MUST_BE_DIFFERENT
import static com.travix.medusa.buildingblocks.ArgumentAssertions.NULL_MESSAGE
import static com.travix.medusa.buildingblocks.ArgumentAssertions.STRING_MUST_HAVE_LENGTH_WITH_NON_EMPTY_CHARACTERS
import static com.travix.medusa.buildingblocks.ArgumentAssertions.getErrorMessage
import static com.travix.medusa.busyflights.domain.busyflights.FlightSearch.DESTINATION
import static com.travix.medusa.busyflights.domain.busyflights.FlightSearch.ORIGIN
import static Period.DEPARTURE_DATE
import static Period.DEPARTURE_TIME
import static Period.RETURN_TIME
import static com.travix.medusa.busyflights.domain.busyflights.IATACode.CODE
import static com.travix.medusa.busyflights.domain.busyflights.PassengersNumber.NUMBER

/**
 * Specification for flight search object
 */
class FlightSearchSpec extends Specification {

    // ************************* Flight Search Aggregator ************************* \\

    def "Destination can not be null" () {
        when:
        new FlightSearch(new IATACode('ABC'),  null, Period.of(LocalDate.now(), LocalDate.now()), PassengersNumber.min())

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(NULL_MESSAGE, DESTINATION)
    }

    def "Origin can not be null" () {
        when:
        new FlightSearch(null,  new IATACode('ABC'), Period.of(LocalDate.now(), LocalDate.now()), PassengersNumber.min())

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(NULL_MESSAGE, ORIGIN)
    }

    // seriously :P
    def "Origin and destination can not be the same" () {
        given:
        def origin = new IATACode('ABC')
        def destination = new IATACode('ABC')

        when:
        new FlightSearch(origin, destination, Period.of(LocalDate.now(), LocalDate.now()), PassengersNumber.min())

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(MUST_BE_DIFFERENT, ORIGIN, DESTINATION)
    }

    // ************************* Number of passengers ************************* \\

    def "The minimum number of passengers is 1"() {
        when:
        new PassengersNumber(num as Integer)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(MUST_BE_BETWEEN, NUMBER, 1, 4)

        where:
        num << [-1, -100, 0]
    }

    // ************************* IANA Codes ************************* \\

    def "IATA code can not be null"() {
        when:
        new IATACode(null)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(NULL_MESSAGE, CODE)
    }

    def "IATA code must have 3 non-empty characters"() {
        when:
        new IATACode(code)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(STRING_MUST_HAVE_LENGTH_WITH_NON_EMPTY_CHARACTERS, CODE, 3)

        where:
        code << ["  ", "", "     ", "aa ", "a a", "aaa ", " aaa"]
    }

    // ************************* Search Period ************************* \\

    def "Search period must have a departure time"() {
        when:
        new Period(null, LocalDateTime.now())

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(NULL_MESSAGE, DEPARTURE_TIME)
    }

    def "Search period doesn't need a return"() {
        when:
        new Period(LocalDateTime.now(), null)

        then:
        noExceptionThrown()
    }

    def "Return time should be in the future when comparing with departure time"() {
        when:
        new Period(from, to)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(LAST_DATE_CAN_NOT_BE_IN_THE_PAST, RETURN_TIME, DEPARTURE_TIME)

        where:
        from                                          | to
        Dates.isoLocalDateTime("2011-12-03T10:15:30") | Dates.isoLocalDateTime("2011-12-03T10:15:30") // Same date
        Dates.isoLocalDateTime("2011-12-03T10:15:31") | Dates.isoLocalDateTime("2011-12-03T10:15:30") // Date on the past
    }

    def "Return times in the future are valid"() {
        when:
        new Period(from, to)

        then:
        noExceptionThrown()

        where:
        from                                          | to
        Dates.isoLocalDateTime("2011-12-03T10:15:30") | Dates.isoLocalDateTime("2011-12-03T10:15:31") // One second in the future :)
        Dates.isoLocalDateTime("2011-12-03T10:15:30") | Dates.isoLocalDateTime("2050-12-03T10:15:30") // A few years :o
    }

    def "Creating periods from dates must have a departure date"() {
        when:
        Period.of(null, LocalDate.now())

        then:
        def e = thrown(IllegalArgumentException)
        e.message == getErrorMessage(NULL_MESSAGE, DEPARTURE_DATE)
    }

    def "Creating periods from dates instead of times, should set departure at the beginning and return at the end of the day"() {
        given:
        def response

        when:
        response = Period.of(from, to)

        then:
        response.departureTime == from.atStartOfDay()
        assert to == null || response.returnTime == LocalDateTime.of(to, LocalTime.MAX)

        where:
        from                             | to
        Dates.isoLocalDate("2011-12-03") | Dates.isoLocalDate("2011-12-03")
        Dates.isoLocalDate("2011-12-03") | null
    }

}
