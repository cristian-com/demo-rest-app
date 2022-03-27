package com.travix.medusa.busyflights.domain.busyflights.web;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public final class FlightsRestContract {

    private FlightsRestContract() {
    }

    public record Request(@NotNull @NotBlank String origin,
                          @NotNull @NotBlank String destination,
                          @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
                          @NotNull @Min(1) Integer numberOfPassengers) {
    }

    public record Response(AirportOptions outwardOptions, AirportOptions returnOptions) { }

    public record AirportOptions(String code, List<FlightOption> options) { }

    public record FlightOption(String airline,
                               String supplier,
                               String fare,
                               String departureTime,
                               String returnTime) {
    }

}
