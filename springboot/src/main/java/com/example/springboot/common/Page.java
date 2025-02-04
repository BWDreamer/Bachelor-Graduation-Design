package com.example.springboot.common;

import lombok.Data;

import java.util.List;

/**
 * 功能：
 * 作者：王柄皓
 * 日期：2025/2/4 17:47
 */
@Data
public class Page<T> {
    private Integer total;
    private List<T> list;
}
