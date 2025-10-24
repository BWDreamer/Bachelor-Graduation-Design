package com.example.springboot.service;

import com.example.springboot.entity.User;
import com.example.springboot.entity.Collect;
import com.example.springboot.mapper.CollectMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CollectService {

    @Resource
    CollectMapper collectMapper;

    public void set(Collect collect) {
        User currentUser = TokenUtils.getCurrentUser();
        collect.setUserId(currentUser.getId());
        Collect dblCollect = collectMapper.selectUserCollect(collect);
        if (dblCollect == null) {
            collectMapper.insert(collect);
        } else {
            collectMapper.deleteById(dblCollect.getId());
        }
    }

    /**
     * 查询当前用户是否收藏过
     */
    public Collect selectUserCollect(Integer fid, String module) {
        User currentUser = TokenUtils.getCurrentUser();
        Collect collect = new Collect();
        collect.setUserId(currentUser.getId());
        collect.setFid(fid);
        collect.setModule(module);
        return collectMapper.selectUserCollect(collect);
    }

    public int selectByFidAndModule(Integer fid, String module) {
        return collectMapper.selectByFidAndModule(fid, module);
    }

}