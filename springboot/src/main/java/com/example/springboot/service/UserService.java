package com.example.springboot.service;

import com.example.springboot.common.Page;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：
 * 作者：王柄皓
 * 日期：2025/1/24 16:30
 */
@Service //该注解把该类放进Spring容器中
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    public void batchDeleteUser(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteUser(id);
        }
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public List<User> selectByName(String name) {
        return userMapper.selectByName(name);
    }

    public List<User> selectByMore(String username, String name) {
        return userMapper.selectByMore(username, name);
    }

    public List<User> selectByFuzzy(String username, String name) {
        return userMapper.selectByFuzzy(username, name);
    }

    public Page<User> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
        Integer skipNum = (pageNum - 1) * pageSize;  // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5

        Page<User> page = new Page<>();
        List<User> userList = userMapper.selectByPage(skipNum, pageSize, username, name);
        Integer total = userMapper.selectCountByPage(username, name);
        page.setTotal(total);
        page.setList(userList);
        return page;
    }
}
