package com.lingjing.doujuan.infrastructure.enums;


public enum Gender {
    MALE("男"),
    FEMALE("女"),
    OTHER("其他");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

}
