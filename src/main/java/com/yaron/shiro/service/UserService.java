package com.yaron.shiro.service;

import com.yaron.shiro.entity.User;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/06
 * @description
 */
public interface UserService {

    /**
     * 注册
     * @param user
     */
    void register(User user);

    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    User findUserByUserName(String userName);
}
