package com.ooad.dormitory.entity;

public enum Gender {
    FEAMLE(0), MALE(1);

    private final int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
