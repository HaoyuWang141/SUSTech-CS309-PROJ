package com.ooad.dormitory.entity;

public enum Stage {
    TEAM(0), COLLECTION(1), SELECTION(2), FINISH(3);
    private final int value;

    Stage(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
