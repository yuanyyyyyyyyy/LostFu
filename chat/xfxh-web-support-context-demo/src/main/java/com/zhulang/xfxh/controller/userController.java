package com.zhulang.xfxh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhulang.xfxh.pojo.User;

import  com.zhulang.xfxh.serviceImpl.UserServiceImpl;
import com.zhulang.xfxh.serviceImpl.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.hutool.core.lang.Console.log;

@Slf4j
@RestController
@RequestMapping("/user")
public class userController {
    @Resource
    UserServiceImpl service;
    @Resource
    private VerificationCodeService verificationCodeService;
    /**
     * 根据用户ID获取用户信息。
     * 本方法通过GET请求访问，路径为/userInfo，请求参数为id，类型为Long。
     * 获取指定ID的用户信息，并响应返回给客户端。
     * @param id 用户的唯一标识符。这个参数是必需的，用于精确地检索特定用户的信息。
     * @return 返回一个User对象，该对象包含了与提供的ID匹配的用户的所有信息。
     */
    @GetMapping("/userInfo")
    public User userInfo(@RequestParam("id") Long id) throws JsonProcessingException {
        log.info("Id为{}的用户请求获取个人信息",id);
        return  service.getUserById(id);
    }
    @PostMapping("/register")
    public String register(@RequestBody User user){
        log.info("正在处理注册流程，用户详细信息为{}",user);
        String username = user.getUsername();
        if (service.getUserByUsername(username)!=null){
            log.info("用户名{}已被占用",username);
            return "用户名已被占用";
        }
        service.saveUser(user);
        return "注册成功";
    }

    //
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        log.info("正在处理登录流程，username,{}, password,{}",user.getUsername(),user.getPassword());
       return  service.Login(user.getUsername(),user.getPassword());
    }
//用户请求发送邮件
    @GetMapping("/sendCode")
    public String sendVerificationCode(@RequestParam("email") String email) {
        log("{}请求发送邮件",email);
        try {
            verificationCodeService.sendVerificationCode(email);
            return "发送成功";
        } catch (Exception e) {
            return "请稍后再试";
        }
    }
    //用户使用邮箱登录
    @GetMapping("/verifyCode")
    public Object verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        log.info("使用邮箱,{}登录",email);
        boolean isValid = verificationCodeService.verifyCode(email, code);
        if (isValid) {
           User user= service.FindUserByEmail(email);
            return service.generate(user);
        } else {
            return "验证码错误";
        }
    }
    //修改用户
    @PostMapping("/updateuser")
    public String updateuser(@RequestBody User user)
    {
        System.out.println(user);
        log.info("更新");
        service.updateUser(user);
        return "更新成功";
    }


}
