package com.travix.medusa.busyflights.domain.busyflights.gateways.crazyair;

import com.travix.medusa.busyflights.buildingblocks.ExchangeTrait;
import com.travix.medusa.busyflights.buildingblocks.utils.Dates;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * I'm leaving this impl a bit raw just because of time.
 * <p>
 * I would tackle it:
 * <p>
 * - Creating first an HTTP component that would be shared across all gateways.
 * It could be using the spring.WebClient, apache client, any other client really
 * but would go for the spring one just because integrates nicely even if my impl is not really reactive
 */
@Component
public class CrazyAirApi {

    public record CrazyAirRequest(String origin, String destination, String departureDate,
                                  String returnDate, int passengerCount) {
    }

    public record CrazyAirResponse(String airline, double price, String cabinclass,
                                   String departureAirportCode, String destinationAirportCode,
                                   String departureDate, String arrivalDate) {
    }

    private final GET get = new GET();

    public GET get() {
        return get;
    }

    // **************************** Stubbing **************************** \\

    public static class GET implements ExchangeTrait<List<CrazyAirResponse>, CrazyAirRequest> {
        @Override
        public List<CrazyAirResponse> exchange(CrazyAirRequest crazyAirRequest) {
            Random rand = new Random();
            int results = rand.nextInt(5, 10);

            List<CrazyAirResponse> wayThereFlights = IntStream.range(0, results)
                    .mapToObj(i -> getDummyFlight(rand, crazyAirRequest.origin(), crazyAirRequest.destination(), crazyAirRequest.departureDate()))
                    .toList();

            List<CrazyAirResponse> wayBackFlights = crazyAirRequest.returnDate() == null ? Collections.emptyList() :
                    IntStream.range(0, results)
                            .mapToObj(i -> getDummyFlight(rand, crazyAirRequest.destination(), crazyAirRequest.origin(), crazyAirRequest.returnDate()))
                            .toList();

            return Stream.of(wayThereFlights, wayBackFlights).flatMap(List::stream).toList();
        }
    }

    private static CrazyAirResponse getDummyFlight(Random rand,
                                                   String origin,
                                                   String destination,
                                                   String reqTime) {
        List<String> cabinDummyValues = Arrays.asList("E", "B");
        LocalDateTime time = Objects.requireNonNull(
                        Dates.isoLocalDate(reqTime))
                .atStartOfDay();

        return new CrazyAirResponse(
                "AIRLINE_" + rand.nextInt(4),
                rand.nextDouble(200d, 9999999d),
                cabinDummyValues.get(rand.nextInt(cabinDummyValues.size())),
                origin,
                destination,
                time.plusMinutes(rand.nextInt(50, 180))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                time.plusHours(rand.nextInt(4, 30))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}
