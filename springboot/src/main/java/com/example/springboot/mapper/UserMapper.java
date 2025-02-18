package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper //该注解把UserMapper类放进Spring容器中
public interface UserMapper {

    @Insert("insert into `user` (username, password, name, phone, email, address, avatar, role) " +
            "values (#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar}, #{role})")
    void insert(User user);

    @Update("update `user` set username = #{username}, password = #{password}, name= #{name}, phone = #{phone}, " +
            "email = #{email}, address = #{address}, avatar = #{avatar} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from `user` where id = #{id}")
    void deleteUser(Integer id);

    @Select("select * from `user`")
    List<User> selectAll();

    @Select("select * from `user` where id = #{id}")
    User selectById(Integer id);

    @Select("select * from `user` where name = #{name}")
    List<User> selectByName(String name);

    @Select("select * from `user` where username = #{username} and name = #{name}")
    List<User> selectByMore(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
    List<User> selectByFuzzy(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc limit #{skipNum}, #{pageSize}")
    List<User> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize")Integer pageSize, @Param("username") String username, @Param("name") String name);

    @Select("select count(id) from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
    int selectCountByPage(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username = #{username}")
    User selectByUsername(String username);
}
