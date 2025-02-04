package com.example.springboot.Controller;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：提供接口返回数据
 * 作者：王柄皓
 * 日期：2025/1/21 18:45
 */
@RestController//RestController 注解表示该类是一个控制器，并且返回的是JSON格式的数据
@RequestMapping("/web")//"/web"是整个类的路由,即一级路由
public class WebController {
    /**
     * RequestMapping 注解表示处理所有HTTP请求的方法
     * 格式：@RequestMapping(method = RequestMethod.POST),简写为@PostMapping
     * 从地址栏只能发送get请求，测试其他请求可以在postman中测试
     * 常用的请求有：GET,POST,PUT,DELETE
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result msg1(String name){
        return Result.success(name);
    }

    /**
     * PostMapping("/post")中，/post是路由(二级路由)
     * post接口用来新增数据，参数要定义一个对象来接收
     * RequestBody可以提交json数据
     */
    @PostMapping("/post")
    public Result post(@RequestBody Obj obj){
        return Result.success(obj);
    }

    /**
     * put接口用来更新数据
     */
    @PutMapping("/put")
    public Result put(@RequestBody Obj obj){
        return Result.success(obj);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(id);
    }

}
