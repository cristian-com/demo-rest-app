package com.travix.medusa.busyflights.domain.busyflights.services

import com.travix.medusa.busyflights.buildingblocks.utils.Dates
import com.travix.medusa.busyflights.domain.busyflights.Flight
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplier
import com.travix.medusa.busyflights.domain.busyflights.IATACode
import com.travix.medusa.busyflights.domain.busyflights.TimePeriod
import spock.lang.Specification

import java.time.LocalDate

class FlightQueryHandlerSpec extends Specification {

    FlightSupplierGateway $1_flightSupplier = Stub(FlightSupplierGateway) {
        query(_ as FlightSearch) >> dummyFlights().subList(0, 3)
    }

    FlightSupplierGateway $2_flightSupplier = Stub(FlightSupplierGateway) {
        query(_ as FlightSearch) >> dummyFlights().subList(3, 4)
    }

    FlightQueryHandler target

    def setup() {
        target = new FlightQueryHandler([$1_flightSupplier, $2_flightSupplier])
    }

    def "Querying flights should return flights from all suppliers ordered by price" () {
        given:
        List<Flight> expectedFlights = dummyFlights().sort { it.fare() }
        List<Flight> response

        when:
        response = target.handle(new FlightQueryHandler.FlightQuery(
                UUID.randomUUID(),
                'ABV',
                'DBV',
                Dates.isoLocalDate("2011-12-03"),
                Dates.isoLocalDate("2011-12-03"),
                3
        ))

        then:
        assert expectedFlights == response
    }

    List<Flight> dummyFlights() {
        [
                new Flight('AIRLINE_1',
                        FlightSupplier.CRAZY_AIR,
                        BigDecimal.valueOf(1000.999),
                        new IATACode('ABC'),
                        new IATACode('BCD'),
                        TimePeriod.of(LocalDate.now(), LocalDate.now())
                ),
                new Flight('AIRLINE_2',
                        FlightSupplier.CRAZY_AIR,
                        BigDecimal.valueOf(6000.12345),
                        new IATACode('ABC'),
                        new IATACode('DEF'),
                        TimePeriod.of(LocalDate.now(), LocalDate.now())
                ),
                new Flight('AIRLINE_3',
                        FlightSupplier.THOUGH_JET,
                        BigDecimal.valueOf(900),
                        new IATACode('ABC'),
                        new IATACode('AAA'),
                        TimePeriod.of(LocalDate.now(), LocalDate.now())
                ),
                new Flight('AIRLINE_4',
                        FlightSupplier.CRAZY_AIR,
                        BigDecimal.valueOf(919.8888),
                        new IATACode('TXT'),
                        new IATACode('ITK'),
                        TimePeriod.of(LocalDate.now(), LocalDate.now())
                )
        ]
    }

}
