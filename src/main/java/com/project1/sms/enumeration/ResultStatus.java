package com.project1.sms.enumeration;

import lombok.Getter;

@Getter
public enum ResultStatus {

    ACTIVE("Pending"),
    SUSPENDED("Approved");

    private final String label;

    ResultStatus(String label) {
        this.label = label;
    }

}