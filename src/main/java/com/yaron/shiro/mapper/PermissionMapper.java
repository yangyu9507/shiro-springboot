package com.yaron.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaron.shiro.entity.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色id查询权限
     * @author Christy
     * @date 2020/12/01 22:42
     * @param roleId
     * @return java.util.List<com.christy.mplus.model.entity.Permission>
     */
    @Select("select p.id,p.name,p.url from t_permission p left join t_role_permission rp on rp.permission_id = p.id where rp.role_id = #{roleId}")
    List<Permission> getPermissionsByRoleId(Integer roleId);
}
