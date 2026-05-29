package com.project1.sms.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Active {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String label;

    Active(String label){
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
              }

    @JsonCreator
    public static Active from(String value) {

        for (Active active : Active.values()) {

            if (active.label.equalsIgnoreCase(value)) {
                return active;
            }
        }

        throw new IllegalArgumentException(
                "Invalid active status: " + value
        );
    }

}
