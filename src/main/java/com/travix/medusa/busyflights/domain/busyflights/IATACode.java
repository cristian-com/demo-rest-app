package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.buildingblocks.ArgumentAssertions;
import com.travix.medusa.buildingblocks.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class IATACode implements ValueObject {

    // TODO: This needs a timezone
    public static final String CODE = "Code";
    private final int LENGTH = 3;

    private final String textCode;

    public IATACode(String code) {
        // Not checking nullability because "assertNonEmptyLength" does the job
        // Although in that case the message should be quite misleading, as I would like to see "Must not be null"
        this.textCode = ArgumentAssertions.assertNonEmptyLength(CODE, LENGTH, code);
    }

}
