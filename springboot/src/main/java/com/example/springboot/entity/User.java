package com.example.springboot.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：王柄皓
 * 日期：2025/1/24 16:16
 */
@Data //该注解是lombok插件里的，会自动生成get方法和set方法
public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String token;
}
