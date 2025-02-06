package com.example.springboot.exception;

/**
 * 功能：
 * 作者：王柄皓
 * 日期：2025/2/5 19:51
 */
public class ServiceException extends RuntimeException{

    public ServiceException(String msg){
        super(msg);
    }
}
