package com.lingjing.doujuan.infrastructure.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Gender {
    MALE("男"),
    FEMALE("女"),
    OTHER("其他");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    //获取所有的性别集合
    public static List<GenderItem> getAllGenders() {
        return Arrays.stream(values()).map(c ->new GenderItem(c.name(), c.description)).collect(Collectors.toList());
    }

    @AllArgsConstructor
    private static class GenderItem {
        private String name;
        private String description;
    }
}
