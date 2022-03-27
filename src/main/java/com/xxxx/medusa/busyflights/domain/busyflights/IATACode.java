package com.xxxx.medusa.busyflights.domain.busyflights;

import com.xxxx.medusa.busyflights.buildingblocks.utils.ArgumentAssertions;
import com.xxxx.medusa.busyflights.buildingblocks.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class IATACode implements ValueObject {

    public static final String CODE = "Code";
    private final int LENGTH = 3;

    private final String textCode;

    public IATACode(String code) {
        ArgumentAssertions.assertNonNull(CODE, code);
        this.textCode = ArgumentAssertions.assertNonEmptyLength(CODE, LENGTH, code);
    }

}
