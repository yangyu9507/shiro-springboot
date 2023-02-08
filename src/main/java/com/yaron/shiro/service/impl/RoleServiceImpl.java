package com.yaron.shiro.service.impl;


import com.yaron.shiro.entity.Role;
import com.yaron.shiro.mapper.RoleMapper;
import com.yaron.shiro.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/07
 * @description
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {


    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return roleMapper.getRolesByUserId(userId);
    }

}
