package com.travix.medusa.busyflights.domain.busyflights.gateways

import com.travix.medusa.busyflights.buildingblocks.utils.Dates
import com.travix.medusa.busyflights.domain.busyflights.Flight
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplier
import com.travix.medusa.busyflights.domain.busyflights.IATACode
import com.travix.medusa.busyflights.domain.busyflights.TimePeriod
import com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet.ThoughJetApi
import com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet.ThoughJetApi.ToughJetResponse
import com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet.ThoughJetFlightSupplier
import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
 * The problem: Support for fetching flights from multiple suppliers (ToughJet)
 *
 * Samples:
 *
 ** ToughJet API**

 **Request**

 | Name | Description |
 | ------ | ------ |
 | from | 3 letter IATA code(eg. LHR, AMS) |
 | to | 3 letter IATA code(eg. LHR, AMS) |
 | outboundDate |ISO_LOCAL_DATE format |
 | inboundDate | ISO_LOCAL_DATE format |
 | numberOfAdults | Number of passengers |

 **Response**

 | Name | Description |
 | ------ | ------ |
 | carrier | Name of the Airline |
 | basePrice | Price without tax(doesn't include discount) |
 | tax | Tax which needs to be charged along with the price |
 | discount | Discount which needs to be applied on the price(in percentage) |
 | departureAirportName | 3 letter IATA code(eg. LHR, AMS) |
 | arrivalAirportName | 3 letter IATA code(eg. LHR, AMS) |
 | outboundDateTime | ISO_INSTANT format |
 | inboundDateTime | ISO_INSTANT format |
 *
 */
class ThoughJetFlightsSupplierSpec extends Specification {

    ThoughJetFlightSupplier target

    def setup() {
        def GET = Stub(ThoughJetApi.GET) {
            exchange(*_) >> [thoughJetResponse()]
        }

        target = new ThoughJetFlightSupplier(Stub(ThoughJetApi) {
            get() >> GET
        })
    }

    def "ThoughJet flights query"() {
        given:
        def response

        when:
        response = target.query(createFlightSearch())
        if (response instanceof List) response = response[0]

        then:
        assert response == createResponse(thoughJetResponse())
    }

    Flight createResponse(ToughJetResponse thoughJetResponse) {
        new Flight(thoughJetResponse.carrier(),
                FlightSupplier.THOUGH_JET,
                BigDecimal.valueOf(100d),
                new IATACode(thoughJetResponse.departureAirportName()),
                new IATACode(thoughJetResponse.arrivalAirportName()),
                new TimePeriod(
                        Dates.isoLocalDateTime(thoughJetResponse.outboundDateTime(), DateTimeFormatter.ISO_DATE),
                        Dates.isoLocalDateTime(thoughJetResponse.inboundDateTime(), DateTimeFormatter.ISO_DATE)))
    }

    ToughJetResponse thoughJetResponse() {
        new ToughJetResponse(
                'CARRIER',
                100d,
                10d,
                10d,
                'LHR',
                'ABC',
                '2011-12-03T10:15:30Z',
                '2011-12-05T10:15:30Z'
        )
    }

    private static FlightSearch createFlightSearch() {
        new FlightSearch(UUID.randomUUID(),
                'ABC',
                'BCD',
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                2)
    }

}
