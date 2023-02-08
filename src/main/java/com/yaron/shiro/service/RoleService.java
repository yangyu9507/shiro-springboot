package com.yaron.shiro.service;

import com.yaron.shiro.entity.Role;

import java.util.List;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/07
 * @description
 */
public interface RoleService {

    /**
     * 根据用户id获取用户的角色集合
     *
     * @param userId
     * @return
     */
    List<Role> getRolesByUserId(Integer userId);
}
