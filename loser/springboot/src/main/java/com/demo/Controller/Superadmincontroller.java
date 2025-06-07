package com.demo.Controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.Pojo.actionlog;
import com.demo.Pojo.user;
import com.demo.Pojo.userapply;
import com.demo.Response.response;
import com.demo.Response.setRe;
import com.demo.Service.*;
import com.demo.utils.NowTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/superAdmin")
public class Superadmincontroller {


    @Autowired
    Actionlogservice logservice;
    @Autowired
    Userapplyservice userapplyservice;
    @Autowired
    Userservice userservice;

    //查看所有用户
    @GetMapping("/seeWant")
    public response seeWant()
    {

        return setRe.setSu(userapplyservice.selectList(new EntityWrapper<userapply>()));//查看申请表
    }

    // 添加管理员
    @GetMapping("/adAdmin")
    public response addAdmin(@RequestParam(value = "id") int id)
    {
        user u = userservice.selectById(id);
        if(u!=null){
            log.info("更新权限");
            u.setAuth(2);
            userservice.updateById(u);
        }else{
            log.error("用户不存在");
        return     setRe.setUserNull(null);
        }
        logservice.insert(new actionlog(1,id, NowTime.Now(),"添加管理员身份"));
        return setRe.setSu(null);//添加管理员
    }
    @GetMapping("/deleteAdmin")
    public response deleteAdmin(@RequestParam(value = "id") int id)
    {
        log.info("删除管理员");
        user u=new user();
        u=userservice.selectById(id);
        u.setAuth(1);
        userservice.updateById(u);
        logservice.insert(new actionlog(1,id, NowTime.Now(),"被剥夺管理员身份"));
        return setRe.setSu(null);//删除管理员
    }
    @GetMapping("/seelog")
    public response seelog()
    {
        log.info("查看日志");
        return setRe.setSu(logservice.selectList(null));//查看日志
    }
    @GetMapping("/against")
    public  response against(@RequestParam(value = "username") String username){
        log.info("驳回请求");
        user u = userservice.selectOne(new EntityWrapper<user>().eq("username",username));
        log.info(u.getUsername());
        userapplyservice.delete( new EntityWrapper<userapply>().eq("username",u.getUsername()));
        logservice.insert(new actionlog(1,u.getId(), NowTime.Now(),"驳回管理员申请"));
        return setRe.setSu(null);
    }
}
