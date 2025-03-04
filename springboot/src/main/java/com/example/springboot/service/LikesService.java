package com.example.springboot.service;

import com.example.springboot.entity.Likes;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.LikesMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikesService {

    @Resource
    LikesMapper likesMapper;

    public void set(Likes likes) {
        User currentUser = TokenUtils.getCurrentUser();
        likes.setUserId(currentUser.getId());
        Likes dblLikes = likesMapper.selectUserLikes(likes);
        if (dblLikes == null) {
            likesMapper.insert(likes);
        } else {
            likesMapper.deleteById(dblLikes.getId());
        }
    }

    /**
     * 查询当前用户是否点过赞
     */
    public Likes selectUserLikes(Integer fid, String module) {
        User currentUser = TokenUtils.getCurrentUser();
        Likes likes = new Likes();
        likes.setUserId(currentUser.getId());
        likes.setFid(fid);
        likes.setModule(module);
        return likesMapper.selectUserLikes(likes);
    }

    public int selectByFidAndModule(Integer fid, String module) {
        return likesMapper.selectByFidAndModule(fid, module);
    }

}