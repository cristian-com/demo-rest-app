package com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet;

import com.travix.medusa.busyflights.buildingblocks.Adapter;
import com.travix.medusa.busyflights.buildingblocks.utils.Dates;
import com.travix.medusa.busyflights.domain.busyflights.Flight;
import com.travix.medusa.busyflights.domain.busyflights.FlightSearch;
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplier;
import com.travix.medusa.busyflights.domain.busyflights.IATACode;
import com.travix.medusa.busyflights.domain.busyflights.TimePeriod;
import com.travix.medusa.busyflights.domain.busyflights.services.FlightSupplierGateway;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static com.travix.medusa.busyflights.buildingblocks.utils.BigDecimals.*;

@Component
public class ThoughJetFlightSupplier implements FlightSupplierGateway {

    private final ThoughJetFlightSupplier.ThoughJetGetFlightsResponseAdapter adapter;
    private final ToughJetApi toughJetApi;

    public ThoughJetFlightSupplier(ToughJetApi toughJetApi) {
        this.toughJetApi = Objects.requireNonNull(toughJetApi);
        this.adapter = new ThoughJetFlightSupplier.ThoughJetGetFlightsResponseAdapter();
    }

    @Override
    public List<Flight> query(FlightSearch flightSearch) {
        Objects.requireNonNull(flightSearch);

        var request = new ToughJetApi.ToughJetRequest(
                flightSearch.getOrigin().getTextCode(),
                flightSearch.getDestination().getTextCode(),
                Dates.isoLocalDate(flightSearch.getTimePeriod().getDepartureTime()),
                Dates.isoLocalDate(flightSearch.getTimePeriod().getReturnTime()),
                flightSearch.getPassengers().getNumber()
        );

        List<ToughJetApi.ToughJetResponse> responses = toughJetApi.get().exchange(request);

        return adapter.adapt(responses);
    }

    private static class ThoughJetGetFlightsResponseAdapter implements
            Adapter<ToughJetApi.ToughJetResponse, Flight> {

        @Override
        public Flight adapt(ToughJetApi.ToughJetResponse response) {
            return new Flight(
                    response.carrier(),
                    FlightSupplier.THOUGH_JET,
                    adaptFare(response.basePrice(), response.discount(), response.tax()),
                    new IATACode(response.departureAirportName()),
                    new IATACode(response.arrivalAirportName()),
                    new TimePeriod(Dates.isoLocalDateTime(response.outboundDateTime(), DateTimeFormatter.ISO_INSTANT),
                            Dates.isoLocalDateTime(response.inboundDateTime(), DateTimeFormatter.ISO_INSTANT))
            );
        }

        /**
         * Tax + [price - (%) discount]
         */
        private static BigDecimal adaptFare(double price, double discount, double tax) {
            if (price < 0 || tax < 0) {
                // Illegal ?
                throw new IllegalStateException();
            }

            if (discount > 99) {
                // Illegal ?
                throw new IllegalStateException();
            }

            BigDecimal basePrice = BigDecimal.valueOf(price);
            BigDecimal totalDiscount = discount <= 0 ? BigDecimal.ZERO : percentage(basePrice, BigDecimal.valueOf(discount));

            return basePrice.subtract(totalDiscount).add(BigDecimal.valueOf(tax));
        }

    }

}
