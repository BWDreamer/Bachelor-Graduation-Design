package com.example.springboot.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：王柄皓
 * 日期：2025/3/25 20:57
 */
@Data
public class ActivitySign {
    private Integer id;
    private Integer activityId;
    private Integer userId;
    private String time;
    private String activityName;
    private String userName;
}
