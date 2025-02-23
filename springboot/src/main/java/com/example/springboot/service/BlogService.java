package com.example.springboot.service;

import cn.hutool.core.date.DateUtil;
import com.example.springboot.entity.Blog;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.BlogMapper;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 游戏文章信息业务处理
 **/
@Service
public class BlogService {

    @Resource
    private BlogMapper blogMapper;

    /**
     * 新增
     */
    public void add(Blog blog) {
        blog.setDate(DateUtil.today());
        User currentUser = TokenUtils.getCurrentUser();
        if ("用户".equals(currentUser.getRole())){
            blog.setUserId(currentUser.getId());
        }
        blogMapper.insert(blog);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        blogMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            blogMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Blog blog) {
        blogMapper.updateById(blog);
    }

    /**
     * 根据ID查询
     */
    public Blog selectById(Integer id) {
        return blogMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Blog> selectAll(Blog blog) {
        return blogMapper.selectAll(blog);
    }

    /**
     * 分页查询
     */
    public PageInfo<Blog> selectPage(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectAll(blog);
        return PageInfo.of(list);
    }

}