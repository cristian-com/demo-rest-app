package com.xxxx.medusa.busyflights.buildingblocks;

public interface ExchangeTrait<RESPONSE, BODY> {
    RESPONSE exchange(BODY body);
}
