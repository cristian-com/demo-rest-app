package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.busyflights.buildingblocks.ValueObject;
import com.travix.medusa.busyflights.buildingblocks.utils.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode(of = {"execution", "searchId"})
public class FlightSearchResult implements ValueObject {

    private final Serializable searchId;
    private final Instant execution;
    private final List<Flight> outwardOptions;
    private final List<Flight> returnOptions;

    public FlightSearchResult(@NotNull Serializable searchId,
                              Instant execution,
                              List<Flight> outwardOptions,
                              List<Flight> returnOptions) {
        this.searchId = Objects.requireNonNull(searchId);
        this.execution = Objects.nonNull(execution) ? execution : Instant.now();
        this.outwardOptions = Lists.unmodifiable(outwardOptions);
        this.returnOptions = Lists.unmodifiable(returnOptions);
    }

}
