package com.mbopartners.mbomobile.ui.model;

public enum ExpenseFieldType {
    TEXT("text"),
    BIG_TEXT("bigText"),
    DATE("date"),
    MONEY("money"),
    MENU("menu"),
    CITY_STATE("city/state"),
    NUMBER("number");

    public static final String FIELD_MBO_ID_AMOUNT = "amount";
    private String type;

    ExpenseFieldType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static ExpenseFieldType getValueOf(String stringValue) {
        if (stringValue != null) {
            ExpenseFieldType[] possibleValues = ExpenseFieldType.values();
            for (ExpenseFieldType value : possibleValues) {
                if (stringValue.equals(value.toString())) {
                    return value;
                }
            }
        }
        throw new IllegalArgumentException(stringValue + " is not a constant in " + ExpenseFieldType.class.getSimpleName());
    }
}
