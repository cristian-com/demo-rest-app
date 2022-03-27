package com.travix.medusa.busyflights.buildingblocks.exceptios;

public class ArgumentNotValidException extends IllegalArgumentException {

    public ArgumentNotValidException() {
    }

    public ArgumentNotValidException(String s) {
        super(s);
    }

    public ArgumentNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentNotValidException(Throwable cause) {
        super(cause);
    }

}
