package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 轮播图实体类
 */
@Data
@TableName("carousel")
public class Carousel {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("value")
    private String value;
}