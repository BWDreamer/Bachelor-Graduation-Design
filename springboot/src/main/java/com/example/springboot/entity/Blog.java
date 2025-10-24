package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Objects;

@Data
public class Blog {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 游戏名称
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 简介
     */
    private String descr;

    /**
     * 游戏封面
     */
    private String cover;

    /**
     * 标签
     */
    private String tags;

    /**
     * 发布人ID
     */
    private Integer userId;

    /**
     * 发布时间
     */
    private String date;

    /**
     * 浏览量
     */
    private Integer readCount;

    /**
     * 分类ID
     */
    private Integer categoryId;

    private String categoryName;

    private String userName;

    private User user;

    private Integer likesCount;

    private Boolean userLike; //返回当前浏览的数据是否被当前登陆的用户点赞

    private Integer collectCount;

    private Boolean userCollect; //返回当前浏览的数据是否被当前登陆的用户收藏

    private String status; // 审核状态

    private String comment; // 审核意见

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}