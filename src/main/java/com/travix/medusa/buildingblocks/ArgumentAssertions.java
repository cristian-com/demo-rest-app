package com.travix.medusa.buildingblocks;

import java.time.LocalDateTime;

/**
 * Util class for argument assertions targeting domain validations only and for testing use
 *
 * All methods return the passed argument or the last (considered the target)
 */
public final class ArgumentAssertions {

    private ArgumentAssertions() {}

    public static String NULL_MESSAGE = "%s must not be null.";
    public static String LAST_DATE_CAN_NOT_BE_IN_THE_PAST = "%s must be later than %s.";

    public static <T> T assertNonNull(String name, T target) {
        if (target == null) {
            throw new IllegalArgumentException(getErrorMessage(NULL_MESSAGE, name));
        }
        return target;
    }

    public static LocalDateTime assertInTheFuture(String initialName, LocalDateTime initial,
                                                  String targetName, LocalDateTime target) {
        if (target.isAfter(initial)) {
            return target;
        }

        throw new IllegalArgumentException(getErrorMessage(LAST_DATE_CAN_NOT_BE_IN_THE_PAST, targetName, initialName));
    }

    @VisibleForTesting
    // Target is always the first argument name
    public static String getErrorMessage(String message, Object... argumentNames) {
        return String.format(message, argumentNames);
    }

}
