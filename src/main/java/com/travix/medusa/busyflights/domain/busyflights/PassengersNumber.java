package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.buildingblocks.ArgumentAssertions;
import com.travix.medusa.buildingblocks.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class PassengersNumber implements ValueObject {

    private static final int MAX_NUMBER = 4;
    private static final PassengersNumber MAX = new PassengersNumber(MAX_NUMBER);
    private static final PassengersNumber MIN = new PassengersNumber(1);

    private static final String NUMBER = "Passenger number";

    private final int number;

    public PassengersNumber(int number) {
        this.number = ArgumentAssertions.assertMajorThanZeroAndUpTo(NUMBER, 4, number);
    }

    public static PassengersNumber min() {
        return MIN;
    }

    public static PassengersNumber max() {
        return MAX;
    }

}
