package com.example.springboot.service;

import com.example.springboot.entity.Carousel;
import com.example.springboot.mapper.CarouselMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    public void add(Carousel carousel) {
        carouselMapper.insert(carousel);
    }

    public void deleteById(Long id) {
        carouselMapper.deleteById(id);
    }

    public void deleteBatch(List<Long> ids) {
        carouselMapper.deleteBatchIds(ids);
    }

    public void updateById(Carousel carousel) {
        carouselMapper.updateById(carousel);
    }

    public List<Carousel> selectAll(Carousel carousel) {
        return carouselMapper.selectAll(carousel);
    }

    public PageInfo<Carousel> selectPage(Carousel carousel, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Carousel> list = carouselMapper.selectAll(carousel);
        return PageInfo.of(list);
    }
}
