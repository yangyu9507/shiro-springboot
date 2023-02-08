package com.yaron.shiro.service;

import com.yaron.shiro.entity.Permission;

import java.util.List;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/07
 * @description
 */
public interface PermissionService {

    /**
     * 根据角色id获取权限集合
     * @param roleId
     * @return
     */
    List<Permission> getPermissionsByRoleId(Integer roleId);
}
