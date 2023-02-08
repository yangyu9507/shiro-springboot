package com.yaron.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/06
 * @description
 */
@Controller
public class TestController {

    @GetMapping("/index1")
    public String index(){

        return "index";
    }
}
