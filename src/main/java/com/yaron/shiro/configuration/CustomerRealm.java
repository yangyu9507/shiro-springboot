package com.yaron.shiro.configuration;

import com.yaron.shiro.entity.Permission;
import com.yaron.shiro.entity.Role;
import com.yaron.shiro.entity.User;
import com.yaron.shiro.service.PermissionService;
import com.yaron.shiro.service.RoleService;
import com.yaron.shiro.service.UserService;
import com.yaron.shiro.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/06
 * @description 自定义realm
 */
@Slf4j
public class CustomerRealm extends AuthorizingRealm {

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // 获取主身份信息
        String principal = (String)principals.getPrimaryPrincipal();

        log.info("{} 执行了=>授权doGetAuthorizationInfo", principal);

        // 根据主身份信息获取角色信息
        UserService userService = SpringUtils.getBean(UserService.class);
        User user = userService.findUserByUserName(principal);

        RoleService roleService = SpringUtils.getBean("roleService");

        List<Role> roles = roleService.getRolesByUserId(user.getId());
        if (CollectionUtils.isNotEmpty(roles)){
            SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
            PermissionService permissionService = SpringUtils.getBean("permissionService");
            roles.forEach(role -> {
                auth.addRole(role.getName());

                List<Permission> permissions = permissionService.getPermissionsByRoleId(role.getId());
                if (CollectionUtils.isNotEmpty(permissions)){
                    permissions.forEach(
                            permission -> auth.addStringPermission(permission.getName())
                    );
                }
            });
            return auth;
        }

        return null;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String)token.getPrincipal();

        log.info("{} 执行了=>认证doGetAuthenticationInfo", principal);

        UserService userService = SpringUtils.getBean(UserService.class);

        User user = userService.findUserByUserName(principal);

        if (!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), new CustomerByteSource(user.getSalt()), this.getName());
        }
        return null;
    }


}
