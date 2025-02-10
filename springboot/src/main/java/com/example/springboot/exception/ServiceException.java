package com.example.springboot.exception;

import lombok.Getter;

/**
 * 功能：
 * 作者：王柄皓
 * 日期：2025/2/5 19:51
 */
@Getter
public class ServiceException extends RuntimeException{

    private final String code;

    public ServiceException(String msg){
        super(msg);
        this.code = "500";
    }

    public ServiceException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
