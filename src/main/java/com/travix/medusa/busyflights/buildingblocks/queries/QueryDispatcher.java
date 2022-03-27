package com.travix.medusa.busyflights.buildingblocks.queries;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueryDispatcher {

    public <Q extends Query, R> R dispatch(@NotNull QueryHandler<Q, R> handler, @NotNull Q query) {
        try {
            return handler.handle(query);
        } catch (Exception e) {
            log.error("There was an error processing the query.");
            return null;
        }
    }

}
