package com.yaron.shiro.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/07
 * @description 自定义 Weclome页
 */
@Configuration
public class DefaultViewConfig implements WebMvcConfigurer {

    /**
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //这里的"/"是访问路径，"forward:home.html"是请求转发到的页面名称| 直接到页面,不再进Controller
        registry.addViewController("/").setViewName("login");
        //设置优先级
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

    }
}
