package com.ltclab.bloomdoomseller.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum AccountType {
    CUSTOMER("CUSTOMER"),
    SELLER("SELLER"),
    EDITOR("EDITOR");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

//
//    @JsonValue
//    public String getValue() {
//        return value;
//    }
//
//    @JsonCreator
//    public static Category fromString(String value) {
//        for (Category subCategory : Category.values()) {
//            if (subCategory.getValue().equalsIgnoreCase(value)) {
//                return subCategory;
//            }
//        }
//        throw new IllegalArgumentException("Sub-Category not found by value: " + value);
//    }
}
