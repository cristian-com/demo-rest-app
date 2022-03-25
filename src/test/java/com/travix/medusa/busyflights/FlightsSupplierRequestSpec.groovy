package com.travix.medusa.busyflights

import com.travix.medusa.busyflights.domain.busyflights.FlightSearch
import com.travix.medusa.busyflights.domain.busyflights.gateways.crazyair.CrazyAirFlightSupplierGateway
import spock.lang.Specification

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
 **ToughJet API**

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

    def "CrazyAir gateway should support domain model" () {
        given:
        def target = new CrazyAirFlightSupplierGateway()
        def response

        when:
        target.externalFlights(getValidSearch())

        then:
        assert response
    }

    private static FlightSearch getValidSearch() {
        new FlightSearch().with {


            return it
        }
    }

}
