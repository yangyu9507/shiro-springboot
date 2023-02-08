package com.yaron.shiro.controller;

import com.yaron.shiro.entity.User;
import com.yaron.shiro.service.UserService;
import com.yaron.shiro.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Controller
public class UserController {

    @Resource
    private UserService userService;
    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public String login(String username, String password, String verifyCode, HttpSession session){

        String sessionVerfiyCode = (String) session.getAttribute("verifyCode");
        // 获取当前登录用户
        Subject subject = SecurityUtils.getSubject();

        try {
            if(sessionVerfiyCode.equalsIgnoreCase(verifyCode)){
                // 执行登录操作
                subject.login(new UsernamePasswordToken(username,password));
                // 认证通过后直接跳转到index.jsp
                return "index";
            }
            throw new RuntimeException("验证码错误");

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误~");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误~");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果认证失败仍然回到登录页面
        return "login";
    }

    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        // 退出后仍然会到登录页面z
        return "login";
    }


    @RequestMapping("register")
    public String registerNewUser(User user){
        try {

            userService.register(user);
            return "login";
        }catch (Exception ex){
            log.error("Failed to register # user: {}",user, ex);
        }
        return "login";
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }

    @RequestMapping("getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        //生成验证码
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        //验证码放入session
        session.setAttribute("verifyCode",verifyCode);
        //验证码存入图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtil.outputImage(180,40,os,verifyCode);
    }

}


