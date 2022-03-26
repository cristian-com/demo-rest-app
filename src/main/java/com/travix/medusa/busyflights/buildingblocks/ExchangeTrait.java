package com.travix.medusa.busyflights.buildingblocks;

public interface ExchangeTrait<RESPONSE, BODY> {
    RESPONSE exchange(BODY body);
}
