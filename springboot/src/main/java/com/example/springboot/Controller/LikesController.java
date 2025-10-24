package com.example.springboot.Controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Likes;
import com.example.springboot.service.LikesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/likes")
public class LikesController {

    @Resource
    LikesService likesService;

    // 点赞和取消
    @PostMapping("/set")
    public Result set(@RequestBody Likes likes) {
        likesService.set(likes);
        return Result.success();
    }

}