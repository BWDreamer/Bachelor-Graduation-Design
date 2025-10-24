package com.example.springboot.mapper;

import com.example.springboot.entity.Collect;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {

    void insert(Collect collect);

    Collect selectUserCollect(Collect collect);

    void deleteById(Integer id);

    int selectByFidAndModule(@Param("fid") Integer fid, @Param("module") String module);
}