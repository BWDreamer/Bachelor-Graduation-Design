package com.example.springboot.entity;

import lombok.Data;

/**
 * 功能：收藏模块
 * 作者：王柄皓
 * 日期：2025/3/4 10:41
 */
@Data
public class Collect {
    private Integer id;
    private Integer fid;
    private Integer userId;
    private String module;
}