package com.project1.sms.enumeration;

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

}
