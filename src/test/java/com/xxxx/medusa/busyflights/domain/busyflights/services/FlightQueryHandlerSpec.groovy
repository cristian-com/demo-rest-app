package com.xxxx.medusa.busyflights.domain.busyflights.services


import com.xxxx.medusa.busyflights.domain.busyflights.FlightSearchResult
import com.xxxx.medusa.busyflights.domain.busyflights.FlightSupplier
import com.xxxx.medusa.busyflights.domain.busyflights.IATACode
import com.xxxx.medusa.busyflights.buildingblocks.utils.Dates
import com.xxxx.medusa.busyflights.domain.busyflights.Flight
import com.xxxx.medusa.busyflights.domain.busyflights.FlightSearch
import com.xxxx.medusa.busyflights.domain.busyflights.TimePeriod
import spock.lang.Specification

import java.time.LocalDate

class FlightQueryHandlerSpec extends Specification {

    final $1_IATA = 'ABC'
    final $2_IATA = 'BCD'
    final outwardFlights = dummyFlights($1_IATA, $2_IATA)
    final returnFlights = dummyFlights($2_IATA, $1_IATA)

    FlightSupplierGateway $1_flightSupplier = Stub(FlightSupplierGateway) {
        query(_ as FlightSearch) >> {
            outwardFlights.subList(0, 3) + returnFlights.subList(3, 4)
        }
    }

    FlightSupplierGateway $2_flightSupplier = Stub(FlightSupplierGateway) {
        query(_ as FlightSearch) >> {
            outwardFlights.subList(3, 4) + returnFlights.subList(0, 3)
        }
    }

    FlightQueryHandler target

    def setup() {
        target = new FlightQueryHandler([$1_flightSupplier, $2_flightSupplier])
    }

    def "Querying flights should return flights from all suppliers ordered by price"() {
        given:
        List<Flight> expectedOutwardFlights = outwardFlights
        List<Flight> expectedReturnFlights = returnFlights

        FlightSearchResult response

        when:
        response = target.handle(new FlightQueryHandler.FlightQuery(
                UUID.randomUUID(),
                $1_IATA,
                $2_IATA,
                Dates.isoLocalDate("2011-12-03"),
                Dates.isoLocalDate("2011-12-03"),
                3
        )).results

        then:
        verifyAll {
            assert expectedOutwardFlights.toSet() == response.outwardOptions.toSet()
            assert expectedReturnFlights.toSet() == response.returnOptions.toSet()
        }
    }

    List<Flight> dummyFlights(String from, String to) {
        [
                new Flight('AIRLINE_1',
                        FlightSupplier.CRAZY_AIR,
                        BigDecimal.valueOf(1000.999),
                        new IATACode(from),
                        new IATACode(to),
                        TimePeriod.of(LocalDate.now(), LocalDate.now())
                ),
                new Flight('AIRLINE_2',
                        FlightSupplier.CRAZY_AIR,
                        BigDecimal.valueOf(6000.12345),
                        new IATACode(from),
                        new IATACode(to),
                        TimePeriod.of(LocalDate.now(), LocalDate.now())
                ),
                new Flight('AIRLINE_3',
                        FlightSupplier.THOUGH_JET,
                        BigDecimal.valueOf(900),
                        new IATACode(from),
                        new IATACode(to),
                        TimePeriod.of(LocalDate.now(), LocalDate.now())
                ),
                new Flight('AIRLINE_4',
                        FlightSupplier.CRAZY_AIR,
                        BigDecimal.valueOf(919.8888),
                        new IATACode(from),
                        new IATACode(to),
                        TimePeriod.of(LocalDate.now(), LocalDate.now())
                )
        ]
    }

}
