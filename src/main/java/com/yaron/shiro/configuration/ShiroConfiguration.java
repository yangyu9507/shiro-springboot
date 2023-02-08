package com.yaron.shiro.configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yaron.shiro.cache.RedisCacheManager;
import com.yaron.shiro.constants.ShiroConstant;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/06
 * @description shiro 的核心配置类 | 用来整合 shiro 框架
 */
@Configuration
public class ShiroConfiguration {


    /**
     * 登录地址
     */
    @Value("${shiro.login-url}")
    private String loginUrl;

    /**
     * 权限认证失败地址
     */
    @Value("${shiro.unauthorized-url}")
    private String unauthorizedUrl;

    /**
     * 1. 创建ShiroFilter  拦截所有请求
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactorBean(DefaultWebSecurityManager securityManager){

        // 实例化一个shiro的过滤器工厂链
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 给filter 设置安全管理器
        shiroFilter.setSecurityManager(securityManager);

        /*
        https://www.cnblogs.com/HowieYuan/p/9259638.html
            anon:无需认证就可以访问    无参，开放权限，可以理解为匿名用户或游客
            authc:必须认证了才可以访问  无参，需要认证
            user:必须拥有 记住我 功能才能用
            perms:拥有对某个资源的权限才能访问
            role: 拥有某个角色权限才能访问
         */
        //配置系统受限资源
        // 配置系统公共资源
        Map<String, String> map = new LinkedHashMap<>();
        /**
         *  配置不会被拦截的链接
         */
        map.put("/login", DefaultFilter.anon.name()); // anon 设置为公共资源，放行要注意anon和authc的顺序
        map.put("/register", DefaultFilter.anon.name());

        map.put("/getImage", DefaultFilter.anon.name());

        /**
         * 需要认证和授权的链接
         */
        map.put("/index", DefaultFilter.authc.name()); // authc 请示这个资源需要 认证和授权

        // 退出 logout地址，shiro去清除session
        map.put("/logout", "logout");

        // 默认认证界面路径
        shiroFilter.setLoginUrl(loginUrl);
        shiroFilter.setUnauthorizedUrl(unauthorizedUrl);
        shiroFilter.setFilterChainDefinitionMap(map);


        return shiroFilter;
    }

    /**
     * 创建安全管理器
     *
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        return new DefaultWebSecurityManager(realm);
    }

    /**
     * 创建自定义realm
     *
     * @return
     */
    @Bean
    public Realm getRealm(){
        CustomerRealm realm = new CustomerRealm();
        // 设置密码匹配器
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密方式
        matcher.setHashAlgorithmName(ShiroConstant.HASH_ALGORITHM_NAME.MD5);
        // 设置散列次数
        matcher.setHashIterations(ShiroConstant.HASH_ITERATORS);
        //
        realm.setCredentialsMatcher(matcher);

        /**
         * 集成
         */
        // 设置缓存管理器
//        realm.setCacheManager(new EhCacheManager());  // EhCache
        realm.setCacheManager(new RedisCacheManager());
        // 开启全局缓存
        realm.setCachingEnabled(true);
        // 开启认证缓存并指定缓存名称
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthenticationCacheName("authenticationCache");
        // 开启授权缓存并指定缓存名称
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthorizationCacheName("authorizationCache");


        return realm;
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     *
     * 页面上使用shiro标签
     *
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持   (面向切面编程)
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }



}
