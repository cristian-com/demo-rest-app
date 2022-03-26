package com.travix.medusa.busyflights

import com.travix.medusa.buildingblocks.Dates
import com.travix.medusa.busyflights.domain.busyflights.Flight
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplier
import com.travix.medusa.busyflights.domain.busyflights.IATACode
import com.travix.medusa.busyflights.domain.busyflights.TimePeriod
import com.travix.medusa.busyflights.domain.busyflights.gateways.crazyair.CrazyAirApi
import com.travix.medusa.busyflights.domain.busyflights.gateways.crazyair.CrazyAirApi.CrazyAirResponse
import com.travix.medusa.busyflights.domain.busyflights.gateways.crazyair.CrazyAirFlightSupplier
import spock.lang.Specification

import java.time.LocalDate

/**
 * Public API
 * **Busy Flights API**
 *
 | Name | Description |
 | ------ | ------ |
 | origin | 3 letter IATA code(eg. LHR, AMS) |
 | destination | 3 letter IATA code(eg. LHR, AMS) |
 | departureDate | ISO_LOCAL_DATE format |
 | returnDate | ISO_LOCAL_DATE format |
 | numberOfPassengers | Maximum 4 passengers |
 *
 *
 * The problem: Support for fetching flights from multiple suppliers (at least CrazyAir and ToughJet)
 *
 * Samples:
 * **CrazyAir API**
 *
 | Name | Description |
 | ------ | ------ |
 | origin | 3 letter IATA code(eg. LHR, AMS) |
 | destination | 3 letter IATA code(eg. LHR, AMS) |
 | departureDate | ISO_LOCAL_DATE format |
 | returnDate | ISO_LOCAL_DATE format |
 | passengerCount | Number of passengers |

 *
 ** ToughJet API**

 | Name | Description |
 | ------ | ------ |
 | from | 3 letter IATA code(eg. LHR, AMS) |
 | to | 3 letter IATA code(eg. LHR, AMS) |
 | outboundDate |ISO_LOCAL_DATE format |
 | inboundDate | ISO_LOCAL_DATE format |
 | numberOfAdults | Number of passengers |
 *
 */
class FlightsSupplierRequestSpec extends Specification {

    CrazyAirFlightSupplier target

    def setup() {
        def GET = Stub(CrazyAirApi.GET) {
            exchange(*_) >> [crazyAirResponse()]
        }

        target = new CrazyAirFlightSupplier(Stub(CrazyAirApi) {
            get() >> GET
        })
    }

    def "CrazyAir flights query"() {
        given:
        def response

        when:
        response = target.query(createFlightSearch())
        if (response instanceof List) response = response[0]

        then:
        assert response == createResponse(crazyAirResponse())
    }

    Flight createResponse(CrazyAirResponse crazyAirResponse) {
        new Flight(crazyAirResponse.airline(),
                FlightSupplier.CRAZY_AIR,
                BigDecimal.valueOf(crazyAirResponse.price()),
                new IATACode(crazyAirResponse.departureAirportCode()),
                new IATACode(crazyAirResponse.destinationAirportCode()),
                new TimePeriod(Dates.isoLocalDateTime(crazyAirResponse.departureDate()),
                        Dates.isoLocalDateTime(crazyAirResponse.arrivalDate())))
    }

    CrazyAirResponse crazyAirResponse() {
        new CrazyAirResponse(
                "AIRLINE",
                33.0,
                'E',
                'LHR',
                'ABC',
                '2011-12-03T10:15:30',
                '2011-12-05T10:15:30')
    }

    private static FlightSearch createFlightSearch() {
        new FlightSearch('ABC',
                'BCD',
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                2)
    }

}
