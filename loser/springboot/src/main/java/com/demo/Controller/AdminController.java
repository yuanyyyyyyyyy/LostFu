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

// 声明该类为 RESTful 控制器，返回 JSON 数据。
@RestController
// 设置请求路径前缀为 "/admin"，所有方法的请求路径都将以此为前缀。 
@RequestMapping("/admin")
// 使用 Lombok 的 @Slf4j 注解来自动生成日志记录器，方便在类中使用 log 进行日志记录。
@Slf4j
public class AdminController {
    @Autowired
    Userservice userservice;   // 注入用户服务，用于处理用户相关操作。
    @Autowired
    Actionlogservice actionlogservice; // 注入行为日志服务，用于记录用户操作。
    
    //通过用户id,封禁用户，设置权限为0
    @GetMapping("/banuser")
    public response banuser(@RequestParam(name = "id") int id,@RequestParam("calmday")int day)
    {
        log.info("禁言用户");
        user user = null;
        user=userservice.selectById(id);
        user.setAuth(0);  //将用户权限设置为0，表示禁言
        user.setCalmday(day);  //设置禁言天数
        userservice.update(user,new EntityWrapper<user>().eq("id",id));  //更新用户信息
        actionlogservice.insert(new actionlog(1,id, NowTime.Now(),"被禁言"+day+"天")); //记录禁言操作到日志
        return setRe.setSu(null); //返回成功
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
        List<user> userList=userservice.selectList(new EntityWrapper<user>().eq("auth",1)); //声明该类为 RESTful 控制器，返回 JSON 数据。
        List<user> list=userservice.selectList(new EntityWrapper<user>().eq("auth",0)); //声明该类为 RESTful 控制器，返回 JSON 数据。
        userList.addAll(list); //将禁言用户和普通用户合并到一个列表中
        userList.sort(Comparator.comparingInt(user::getId)); //按用户ID排序
        //遍历用户列表，将密码设置为null，避免泄露敏感信息
        for (user a : userList) {
            a.setPassword(null);
        }
        return setRe.setSu(userList); //返回用户列表
    }
    //查看所有管理员信息
    @GetMapping("/alladminInfo")
    public response alladminInfo(){
        List<user> userList=userservice.selectList(new EntityWrapper<user>().eq("auth",2)); //查询所有管理员用户
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
