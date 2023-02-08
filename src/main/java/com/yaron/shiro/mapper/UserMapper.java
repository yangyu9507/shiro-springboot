package com.yaron.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaron.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/06
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where username=#{username}")
    User findUserByusername(@Param("username") String userName);
}
