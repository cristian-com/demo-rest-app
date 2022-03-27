package com.xxxx.medusa.busyflights.buildingblocks.queries;

public interface QueryHandler<Q extends Query, R> {

    R handle(Q query);

}
