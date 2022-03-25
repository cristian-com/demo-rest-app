package com.travix.medusa.buildingblocks;

public class ArgumentNotValidException extends IllegalArgumentException {

    // Targeting testing
    private final String baseMessage;

    public ArgumentNotValidException(String baseMessage, String fullMessage) {
        super(fullMessage);
        this.baseMessage = baseMessage;
    }

    public String getBaseMessage() {
        return baseMessage;
    }

}
