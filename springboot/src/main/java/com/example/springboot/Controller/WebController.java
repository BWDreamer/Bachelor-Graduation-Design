package com.example.springboot.Controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：提供接口返回数据
 * 作者：王柄皓
 * 日期：2025/1/21 18:45
 */
@RestController //RestController 注解表示该类是一个控制器，并且返回的是JSON格式的数据
public class WebController {

    @Resource
    UserService userService;

    @GetMapping("/")
    public Result hello(){
        return Result.success("success");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){ //国产hutool插件
            return Result.error("数据输入不合法");
        }
        user = userService.login(user);
        return Result.success(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){ //国产hutool插件
            return Result.error("数据输入不合法");
        }
        if(user.getUsername().length()>10 || user.getPassword().length()>20){
            return Result.error("数据输入不合法");
        }
        user = userService.register(user);
        return Result.success(user);
    }

}
