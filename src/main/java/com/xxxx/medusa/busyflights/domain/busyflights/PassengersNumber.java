package com.xxxx.medusa.busyflights.domain.busyflights;

import com.xxxx.medusa.busyflights.buildingblocks.utils.ArgumentAssertions;
import com.xxxx.medusa.busyflights.buildingblocks.ValueObject;
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

    protected static final String NUMBER = "Passenger number";

    private final int number;

    public PassengersNumber(int number) {
        this.number = ArgumentAssertions.assertMajorThanZeroAndUpTo(NUMBER, MAX_NUMBER, number);
    }

    public static PassengersNumber min() {
        return MIN;
    }

}
