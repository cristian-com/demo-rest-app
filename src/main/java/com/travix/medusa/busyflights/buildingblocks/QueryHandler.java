package com.travix.medusa.busyflights.buildingblocks;

public interface QueryHandler<Q extends Query, R> {

    R handle(Q query);

}
