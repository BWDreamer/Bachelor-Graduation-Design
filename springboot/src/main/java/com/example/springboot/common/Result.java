package com.example.springboot.common;

import lombok.*;

/**
 * 接口统一返回包装类
 * 作者：王柄皓
 */
@Getter
@Setter
@AllArgsConstructor//创建所有属性的构造函数
@NoArgsConstructor//创建无参构造函数
@Builder
public class Result {

    public static final String CODE_SUCCESS = "200";
    public static final String CODE_AUTH_ERROR = "401";
    public static final String CODE_SYS_ERROR = "500";

    /**
     * code是状态码，200表示成功，500表示后台错误，401表示数据没有权限，
     * 404接口路径写错了或者参数写错了，405接口请求类型不匹配，400接口参数错误
     */
    private String code;
    /**
     * msg表示错误的详细信息
     */
    private String msg;
    /**
     * data表示返回的数据，数据从什么地方返回出去
     * 返回user，Object类型就是user
     * 返回List，Object类型就是List
     * 返回LMap，Object类型就是Map
     */
    private Object data;


    public static Result success() {
        return new Result(CODE_SUCCESS, "请求成功", null);
    }

    public static Result success(Object data) {
        return new Result(CODE_SUCCESS, "请求成功", data);
    }

    public static Result error(String msg) {
        return new Result(CODE_SYS_ERROR, msg, null);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result(CODE_SYS_ERROR, "系统错误", null);
    }

}