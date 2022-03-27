package com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet;

import com.travix.medusa.busyflights.buildingblocks.ExchangeTrait;
import com.travix.medusa.busyflights.buildingblocks.utils.Dates;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * I'm leaving leave this impl a bit raw just because of time.
 * <p>
 * I would tackle it:
 * <p>
 * - Creating first an HTTP component that would be shared across all gateways.
 * It could be using the spring.WebClient, apache client, any other client really
 * but would go for the spring one just because integrates nicely even if my impl is not really reactive
 */
@Component
public class ToughJetApi {

    public record ToughJetRequest(String from, String to, String outboundDate,
                                  String inboundDate, int numberOfAdults) {
    }

    public record ToughJetResponse(String carrier, double basePrice, double tax, double discount,
                                   String departureAirportName, String arrivalAirportName,
                                   String outboundDateTime, String inboundDateTime) {
    }

    private final ToughJetApi.GET get = new ToughJetApi.GET();

    public ToughJetApi.GET get() {
        return get;
    }

    public static class GET implements ExchangeTrait<List<ToughJetApi.ToughJetResponse>,
            ToughJetApi.ToughJetRequest> {
        @Override
        public List<ToughJetApi.ToughJetResponse> exchange(ToughJetApi.ToughJetRequest thoughJetRequest) {
            Random rand = new Random();
            int results = rand.nextInt(5, 10);

            List<ToughJetApi.ToughJetResponse> wayThereFlights = IntStream.range(0, results)
                    .mapToObj(i -> getDummyFlight(rand, thoughJetRequest.from(), thoughJetRequest.to(), thoughJetRequest.outboundDate()))
                    .toList();

            List<ToughJetApi.ToughJetResponse> wayBackFlights = thoughJetRequest.inboundDate() == null ? Collections.emptyList() :
                    IntStream.range(0, results)
                            .mapToObj(i -> getDummyFlight(rand, thoughJetRequest.to(), thoughJetRequest.from(), thoughJetRequest.inboundDate()))
                            .toList();

            return Stream.of(wayThereFlights, wayBackFlights).flatMap(List::stream).toList();
        }
    }

    private static ToughJetApi.ToughJetResponse getDummyFlight(Random rand,
                                                               String origin,
                                                               String destination,
                                                               String reqTime) {
        ZonedDateTime time = Objects.requireNonNull(
                        Dates.isoLocalDate(reqTime))
                .atStartOfDay().atZone(ZoneId.systemDefault());

        return new ToughJetApi.ToughJetResponse(
                "AIRLINE_" + rand.nextInt(4),
                rand.nextDouble(200d, 9999999d),
                rand.nextDouble(100d, 200d),
                rand.nextDouble(5d, 20d),
                origin,
                destination,
                DateTimeFormatter.ISO_INSTANT
                        .format(time.plusMinutes(rand.nextInt(50, 180))),
                DateTimeFormatter.ISO_INSTANT
                        .format(time.plusHours(rand.nextInt(4, 30))));
    }

}
