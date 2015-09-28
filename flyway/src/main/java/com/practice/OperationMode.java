package com.practice;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/28/15
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public enum OperationMode {
    MIGRATE("migrate"),
    REPAIR("repair");

    private String value;

    private OperationMode(String value) {
        this.value = value;
    }

    public static OperationMode fromValue(String value) {
        for (OperationMode mode : OperationMode.values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return null;
    }
}
