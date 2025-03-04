package com.example.springboot.common;

public enum LikesModuleEnum {
    BLOG("游戏文章"),
    ACTIVITY("游戏资讯");

    private String value;

    public String getValue() {
        return value;
    }

    LikesModuleEnum(String value) {
        this.value = value;
    }
}