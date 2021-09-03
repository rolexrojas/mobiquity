package com.mobiquity.types;

public enum CurrencyEnum {

    EUROS("€");

    public final String label;

    private CurrencyEnum(String label) {
        this.label = label;
    }
}
