package com.yaron.shiro.service.impl;

import com.yaron.shiro.constants.ShiroConstant;
import com.yaron.shiro.entity.User;
import com.yaron.shiro.mapper.UserMapper;
import com.yaron.shiro.service.UserService;
import com.yaron.shiro.util.SaltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/06
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 注册
     * @param user
     */
    @Override
    public void register(User user) {

        // 生成随机盐
        String salt = SaltUtil.getSalt(ShiroConstant.SALT_LENGTH);
        // 保存随机盐
        user.setSalt(salt);

        //生成密码
        Md5Hash pwd = new Md5Hash(user.getPassword(), salt, ShiroConstant.HASH_ITERATORS);

        // 保存密码
        user.setPassword(pwd.toHex());
        userMapper.insert(user);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userMapper.findUserByusername(userName);
    }


}
