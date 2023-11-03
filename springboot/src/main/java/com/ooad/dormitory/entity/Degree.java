package com.ooad.dormitory.entity;

public enum Degree {
    BACHELOR(0), MASTER(1), DOCTOR(2);

    private int value;

    Degree(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
