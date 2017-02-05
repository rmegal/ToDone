package com.raymegal.todone;

public enum EditAction {
    ADD_ACTION(76),
    EDIT_ACTION(77),
    DELETE_ACTION(78);

    private final int value;

    private EditAction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
