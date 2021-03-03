package com.food.cpg.models;

/**
 * @author Kartik Gevariya
 */
public enum Unit {
    GRAM("g"),
    LB("lb"),
    FL_OZ("fl oz"),
    GAL("gal"),
    L("L");

    Unit(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    private final String alias;
}
