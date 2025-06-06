package com.demo.Controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.Pojo.actionlog;
import com.demo.Pojo.post;
import com.demo.Pojo.user;
import com.demo.Response.response;
import com.demo.Response.setRe;
import com.demo.Service.Actionlogservice;
import com.demo.Service.Userservice;
import com.demo.utils.NowTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    Userservice userservice;
    @Autowired
    Actionlogservice actionlogservice;
    //通过用户id,封禁用户，设置权限为0
    @GetMapping("/banuser")
    public response banuser(@RequestParam(name = "id") int id,@RequestParam("calmday")int day)
    {
        log.info("禁言用户");
        user user = null;
        user=userservice.selectById(id);
        user.setAuth(0);
        user.setCalmday(day);
        userservice.update(user,new EntityWrapper<user>().eq("id",id));
        actionlogservice.insert(new actionlog(1,id, NowTime.Now(),"被禁言"+day+"天"));
        return setRe.setSu(null);
    }

    //降低用户信誉分
    @GetMapping("/minScore")
    public response reduceSc(@RequestParam(name = "score") int score,@RequestParam(name = "username") String username){
        user u=null;
        u = userservice.selectOne(new EntityWrapper<>(u).eq("username", username));
//        user u = userservice.selectById(uid);//找到用户
        u.setCrscore(u.getCrscore()-score);//扣除信誉分
        actionlogservice.insert(new actionlog(1,u.getId(), NowTime.Now(),"被扣除"+score+"信誉分"));
        userservice.updateById(u);//更新数据
        return setRe.setSu(null);
    }
    //查看所有普通用户信息
    @GetMapping("/allUserInfo")
    public response allUserInfo(){
        List<user> userList=userservice.selectList(new EntityWrapper<user>().eq("auth",1));
        List<user> list=userservice.selectList(new EntityWrapper<user>().eq("auth",0));
        userList.addAll(list);
        userList.sort(Comparator.comparingInt(user::getId));
        for (user a : userList) {
            a.setPassword(null);
        }
        return setRe.setSu(userList);
    }
    //查看所有管理员信息
    @GetMapping("/alladminInfo")
    public response alladminInfo(){
        List<user> userList=userservice.selectList(new EntityWrapper<user>().eq("auth",2));
        for (user a : userList) {
            a.setPassword(null);
        }
        return setRe.setSu(userList);
    }

    @GetMapping("/seelog")
    public response seelog()
    {
        log.info("查看日志");
        return setRe.setSu(actionlogservice.selectList(null));//查看日志
    }
    @GetMapping("/release")
public  response release(@RequestParam("id") int id){
        log.info("解除禁言");
        user user = null;
        user=userservice.selectById(id);
        user.setAuth(1);
        user.setCalmday(0);
        userservice.update(user,new EntityWrapper<user>().eq("id",id));
        actionlogservice.insert(new actionlog(1,id, NowTime.Now(),"解除禁言"));
        return setRe.setSu(null);
    }

}
