package com.example.springboot.Controller;

import com.example.springboot.common.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import java.util.List;



/**
 * 功能：
 * 作者：王柄皓
 * 日期：2025/1/24 16:31
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired //该注解把UserService类从Spring容器中拿出来
    UserService userService;

    /**
     * 功能：新增用户信息
     * RequestBody  User user 接收前端传来的json数据
     * PostMapping("/add")  接收前端传来的post请求
     * post是用来修改数据的
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        try {
            userService.insertUser(user);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("插入数据库错误，用户名已存在");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }

    /**
     * 功能：修改用户信息
     * put 是用来新增和覆盖数据的
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

    /**
     * 功能：删除用户信息
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * 功能：批量删除用户信息
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        userService.batchDeleteUser(ids);
        return Result.success();
    }

    /**
     * 功能：查询全部用户信息
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> userList = userService.selectAll();
        return Result.success(userList);
    }

    /**
     * 功能：根据ID查询用户信息
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }

    /**
     * 功能：根据Name查询用户信息
     * 根据条件查询的时候，如果不确定查询的结果有几个，那就统一返回一个LIst对象集合，这样是最稳妥的方式，不会出现错误！
     */
    @GetMapping("/selectByName/{name}")
    public Result selectByName(@PathVariable String name) {
        List<User> userList = userService.selectByName(name);
        return Result.success(userList);
    }

    /**
     * 功能：多条件查询用户信息
     */
    @GetMapping("/selectByMore")
    public Result selectByMore(@RequestParam String username, @RequestParam String name) {
        List<User> userList = userService.selectByMore(username, name);
        return Result.success(userList);
    }

    /**
     * 功能：多条件模糊查询用户信息
     */
    @GetMapping("/selectByFuzzy")
    public Result selectByFuzzy(@RequestParam String username, @RequestParam String name) {
        List<User> userList = userService.selectByFuzzy(username, name);
        return Result.success(userList);
    }

    /**
     * 多条件模糊查询用户信息
     * pageNum 当前的页码
     * pageSize 每页查询的个数
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String username,
                               @RequestParam String name) {
        Page<User> page = userService.selectByPage(pageNum, pageSize, username, name);
        return Result.success(page);
    }
}
