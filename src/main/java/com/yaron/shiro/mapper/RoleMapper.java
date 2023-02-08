package com.yaron.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaron.shiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/07
 * @description
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询用户的角色
     *
     * @param userId
     * @return
     */
    @Select("select r.id,r.name from t_role r left join t_user_role ur on ur.role_id = r.id where ur.user_id = #{userId}")
    List<Role> getRolesByUserId(Integer userId);

}
