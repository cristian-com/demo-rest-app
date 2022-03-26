package com.travix.medusa.buildingblocks;

public interface ExchangeTrait<RESPONSE, BODY> {
    RESPONSE exchange(BODY body);
}
