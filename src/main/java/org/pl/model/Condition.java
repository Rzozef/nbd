package org.pl.model;

import lombok.ToString;

@ToString
public enum Condition {
    UNREPAIRABLE,
    VERY_BAD,
    BAD,
    AVERAGE,
    DUSTY,
    FINE;

    public static Condition fromValue(String v) {
        return valueOf(v);
    }
}
