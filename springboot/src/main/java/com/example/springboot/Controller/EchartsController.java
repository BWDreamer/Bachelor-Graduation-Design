package com.example.springboot.Controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Blog;
import com.example.springboot.entity.Category;
import com.example.springboot.entity.User;
import com.example.springboot.service.BlogService;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    public CategoryService categoryService;

    @Resource
    public BlogService blogService;

    @Resource
    public UserService userService;

    @GetMapping("/pie")
    public Result pie(){
        List<Map<String, Object>> list = new ArrayList<>();
        //查询出所有的游戏类型
        List<Category> categories = categoryService.selectAll(new Category());
        //查出所有的文章信息
        List<Blog> blogs = blogService.selectAll(new Blog());
        for (Category category : categories) {
            long count = blogs.stream().filter(x -> category.getId().equals(x.getCategoryId())).count();
            Map<String, Object> map = new HashMap<>();
            map.put("name", category.getName());
            map.put("value", count);
            list.add(map);
        }
        return Result.success(list);
    }

    @GetMapping("/bar")
    public Result bar(){
        Map<String, Object> resultMap = new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();

        Map<String, Long> map = new HashMap<>();

        // 查询所有的用户
        List<User> users = userService.list();
        //查出所有的文章信息
        List<Blog> blogs = blogService.selectAll(new Blog());
        for (User user : users) {
            long count = blogs.stream().filter(x -> user.getId() == x.getUserId()).count();
            map.put(user.getName(), count);
        }

        // 对map进行排序，按照value来倒序
        LinkedHashMap<String, Long> collectMap = map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (String key : collectMap.keySet()) {
            xList.add(key);
            yList.add(collectMap.get(key));
        }

        // top几的问题，截断一下
        if (xList.size() > 5 && yList.size() > 5) {
            xList = xList.subList(0, 5);
            yList = yList.subList(0, 5);
        }

        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);
        return Result.success(resultMap);
    }

    @GetMapping("/line")
    public Result line(){
        Map<String, Object> resultMap = new HashMap<>();
        List<Long> yList = new ArrayList<>();

        // 获取最近多少天的年月日List
        Date today = new Date();
        DateTime start = DateUtil.offsetDay(today, -6);
        List<String> xList = DateUtil.rangeToList(start, today, DateField.DAY_OF_YEAR)
                .stream().map(DateUtil::formatDate).collect(Collectors.toList());

        //查出所有的文章信息
        List<Blog> blogs = blogService.selectAll(new Blog());

        for (String day : xList) {
            long count = blogs.stream().filter(x -> ObjectUtil.isNotEmpty(x.getDate()) && x.getDate().contains(day)).count();
            yList.add(count);
        }

        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);
        return Result.success(resultMap);
    }

}
