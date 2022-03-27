package com.travix.medusa.busyflights.domain.busyflights.gateways.toughjet;

import com.travix.medusa.busyflights.buildingblocks.ExchangeTrait;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * I'm leaving leave this impl a bit raw just because of time.
 *
 * I would tackle it:
 *
 * - Creating first an HTTP component that would be shared across all gateways.
 *          It could be using the spring.WebClient, apache client, any other client really
 *               but would go for the spring one just because integrates nicely even if my impl is not really reactive
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
            return Collections.emptyList();
        }
    }

}
