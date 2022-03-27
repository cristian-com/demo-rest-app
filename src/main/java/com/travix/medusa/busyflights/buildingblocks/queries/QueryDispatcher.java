package com.travix.medusa.busyflights.buildingblocks.queries;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
public class QueryDispatcher {

    public <Q extends Query, R> R dispatch(@NotNull QueryHandler<Q, R> handler, @NotNull Q query) {
        try {

            if (log.isTraceEnabled()) {
                log.trace("{} query {} starting with params {}", query.getClass().getName(), query.id(), query);
            }

            R response = handler.handle(query);

            if (log.isTraceEnabled()) {
                log.trace("{} query {} responded with {}", query.getClass().getName(), query.id(), response);
            }

            return response;
        } catch (Exception e) {
            log.error("There was an error processing the query " +
                    query.getClass().getName() + " id: " + query.id(), e);
            throw e;
        }
    }

}
