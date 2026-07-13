package com.vinibarros.optisched.enums;

public enum Term {
    FIRST(1),
    SECOND(2);

    private final int value;

    Term(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Term fromValue(int value) {
        for (Term term : Term.values()) {
            if (term.value == value) {
                return term;
            }
        }
        throw new IllegalArgumentException("Invalid term value: " + value);
    }
}