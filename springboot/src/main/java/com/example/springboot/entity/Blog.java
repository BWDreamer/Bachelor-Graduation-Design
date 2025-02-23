package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 游戏文章信息
 */
@Data
public class Blog {

    /** ID */
    @TableId(type= IdType.AUTO)
    private Integer id;
    /** 游戏名称 */
    private String title;
    /** 内容 */
    private String content;
    /** 简介 */
    private String descr;
    /** 游戏封面 */
    private String cover;
    /** 标签 */
    private String tags;
    /** 发布人ID */
    private Integer userId;
    /** 发布时间 */
    private String date;
    /** 浏览量 */
    private Integer readCount;
    /** 分类ID */
    private Integer categoryId;

    private String categoryName;

    private String userName;

}