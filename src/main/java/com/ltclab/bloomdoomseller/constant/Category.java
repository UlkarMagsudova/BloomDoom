package com.ltclab.bloomdoomseller.constant;

import lombok.Getter;

@Getter
public enum Category {
    PLANT("Plant"),
    POT("Pot"),
    FERTILIZER("Fertilizer"),
    TOOL("Tool"),
    SEED("Seed");

    private final String value;

    Category(String value) {
        this.value = value;
    }

//    @JsonValue
//    public String getValue() {
//        return value;
//    }

//    @JsonCreator
//    public static Category fromString(String value) {
//        for (Category subCategory : Category.values()) {
//            if (subCategory.value.equalsIgnoreCase(value)) {
//                return subCategory;
//            }
//        }
//        throw new IllegalArgumentException("Sub-Category not found by value: " + value);
//    }
}