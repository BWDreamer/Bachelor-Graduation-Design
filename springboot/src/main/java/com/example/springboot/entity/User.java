package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")   //MyBatis-plus注解，指定表名
public class User {

    @TableId(type = IdType.AUTO)
    private int id;

    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String role;

    @TableField(exist = false)
    private String newPassword;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private Integer blogCount;

    @TableField(exist = false)
    private Integer likesCount;

    @TableField(exist = false)
    private Integer collectCount;
}
