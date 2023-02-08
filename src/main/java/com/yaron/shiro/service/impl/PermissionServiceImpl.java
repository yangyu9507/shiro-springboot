package com.yaron.shiro.service.impl;

import com.yaron.shiro.entity.Permission;
import com.yaron.shiro.mapper.PermissionMapper;
import com.yaron.shiro.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/07
 * @description
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionsByRoleId(Integer roleId) {
        return permissionMapper.getPermissionsByRoleId(roleId);
    }

}
