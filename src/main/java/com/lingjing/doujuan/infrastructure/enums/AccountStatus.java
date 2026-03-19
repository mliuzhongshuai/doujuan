package com.lingjing.doujuan.infrastructure.enums;

public enum AccountStatus {
    ACTIVE("ACTIVE"),
    LOCKED("LOCKED"),
    DISABLED("DISABLED");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
