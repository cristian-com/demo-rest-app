package com.travix.medusa.buildingblocks;

import java.time.LocalDateTime;

/**
 * Util class for argument assertions targeting domain validations only and for testing use
 * <p>
 * All methods return the passed argument or the last (target)
 */
public final class ArgumentAssertions {

    private ArgumentAssertions() {
    }

    public static String NULL_MESSAGE = "%s must not be null.";
    public static String LAST_DATE_CAN_NOT_BE_IN_THE_PAST = "%s must be later than %s.";
    public static String STRING_MUST_HAVE_LENGTH_WITH_NON_EMPTY_CHARACTERS = "%s must has at least %d characters, and not contain emtpy spaces.";
    public static String MUST_BE_BETWEEN = "%s must be between %s and %s.";
    public static String MUST_BE_DIFFERENT = "%s must be different to %s.";

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

    public static String assertNonEmptyLength(String name, int length, String target) {
        if (target != null && !target.isBlank() && target.length() == length && !target.contains(" ")) {
            return target;
        }

        throw new IllegalArgumentException(getErrorMessage(STRING_MUST_HAVE_LENGTH_WITH_NON_EMPTY_CHARACTERS, name, length));
    }

    public static int assertBetween(String name, int min, int max, int target) {
        if (target >= min && target <= max) {
            return target;
        }

        throw new IllegalArgumentException(getErrorMessage(MUST_BE_BETWEEN, name, min, max));
    }

    public static int assertMajorThanZeroAndUpTo(String name, int max, int target) {
        return assertBetween(name, 1, max, target);
    }

    public static <T> T assertNonEquals(String baseName, T base, String targetName, T target) {
        if (base == null && target == null) {
            return null;
        }

        if (base != null && !base.equals(target)) {
            return target;
        }

        throw new IllegalArgumentException(getErrorMessage(MUST_BE_DIFFERENT, targetName, baseName));
    }

    @VisibleForTesting
    // Target is always the first argument name
    public static String getErrorMessage(String message, Object... argumentNames) {
        return String.format(message, argumentNames);
    }

}
