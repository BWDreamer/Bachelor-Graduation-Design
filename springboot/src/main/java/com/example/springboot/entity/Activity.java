package com.example.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Activity {

    /**
     * ID
     */
    private Integer id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动简介
     */
    private String descr;

    /**
     * 开始时间
     */
    private String start;

    /**
     * 结束时间
     */
    private String end;

    /**
     * 活动形式
     */
    private String form;

    /**
     * 活动地址
     */
    private String address;

    /**
     * 主办方
     */
    private String host;

    /**
     * 浏览量
     */
    private Integer readCount;

    private String content;

    private String cover;

    @Setter
    @Getter
    private Boolean isEnd;  //活动是否结束

    @Setter
    @Getter
    private Boolean isSign; //是否报名

    private Integer likesCount;

    private Integer collectCount;

    @Setter
    @Getter
    private Boolean isLike; //是否已点过赞

    @Setter
    @Getter
    private Boolean isCollect;  //是否已收藏

    private Integer userId;

}