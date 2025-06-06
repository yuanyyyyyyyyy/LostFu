package com.demo.Controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.Pojo.post;
import com.demo.Pojo.user;
import com.demo.Response.response;
import com.demo.Response.setRe;
import com.demo.Service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController

public class loginController {
    @Autowired
    Userservice userservice;
    @PostMapping("/register")
    public response register(@RequestBody user user){
        log.info("进入注册流程");
        user.setCrscore(100);
        user.setInfoid(1);
        user.setAuth(1);
        userservice.insert(user);
        user.setPassword(null);
        return setRe.setSu(user);
    }
    @PostMapping("/login")
    public response login(@RequestBody user u){
        log.info("进入登录流程");
        String rightpass=null;
        user us = userservice.selectOne(new EntityWrapper<user>().eq("username", u.getUsername()));
        //还要添加判断 infoid 查找这个用户得权限 如果有权限则更新管理员infoid
        if (us != null) {//用户信息是否为空
            rightpass = us.getPassword();
            if(rightpass.equals(u.getPassword())==false){
                return setRe.setFail(null);
            }else{
                if(us.getInfoid()<3){
                    us.setInfoid(u.getInfoid());
                }
                userservice.updateById(us);
                us.setPassword(null);
                StpUtil.login(us.getId());
                SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
                ArrayList<Object> list = new ArrayList<>();
                list.add(us);
                list.add(tokenInfo);
                return setRe.setSu(list);
            }
        } else {
            return setRe.setFail(null);
        }


    }
}
