package com.example.springboot.mapper;

import com.example.springboot.entity.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarouselMapper {

    int insert(Carousel carousel);

    int deleteById(Long id);

    int deleteBatchIds(List<Long> ids);

    int updateById(Carousel carousel);

    List<Carousel> selectAll(@Param("carousel") Carousel carousel);
}