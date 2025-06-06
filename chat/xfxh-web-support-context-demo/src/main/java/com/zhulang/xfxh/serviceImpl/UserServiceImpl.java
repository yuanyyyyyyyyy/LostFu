package com.zhulang.xfxh.serviceImpl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhulang.xfxh.mapper.UserMapper;
import com.zhulang.xfxh.pojo.User;
import com.zhulang.xfxh.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 根据用户ID获取用户信息。
     *
     * 本方法尝试从数据库中根据指定的ID获取用户信息。如果用户信息存在，则返回该用户对象；
     * 如果不存在，则返回null。如果在尝试获取用户信息的过程中发生异常，方法将记录错误日志
     * 并抛出一个运行时异常。
     *
     * @param id 用户的唯一标识符，类型为长整型。
     */
    public User getUserById(Long id) {
        try {
            // 尝试根据ID从数据库中选择用户。
            User user = baseMapper.selectById(id);
            // 如果用户对象不为空，则记录日志表示用户信息已成功获取。
            if (user != null) {
                logger.info("User {} fetched from database", id);
            }
            // 返回获取到的用户对象，可能为null。
            return user;
        } catch (Exception e) {
            // 在发生异常时，记录错误日志并抛出运行时异常。
            logger.error("Failed to get user by id: " + id, e);
            throw new RuntimeException("Error fetching user from database", e);
        }
    }

    /**
     * 向数据库保存用户实体。
     *
     * 该方法尝试使用baseMapper的insert方法将User对象插入数据库。
     * 如果操作成功，它将记录保存用户的资讯。
     * 如果在操作过程中发生异常，它会捕获该异常，记录一条错误消息指示未能保存用户，
     * 并重新抛出一个运行时异常，表明在将用户保存到数据库时发生了错误。
     *
     * @param user 要保存的User对象，包含用户信息。
     */
    public void saveUser(User user) {
        try {
            //加密
            String rawPassword = user.getPassword();
            String encodedPassword = DigestUtils.md5Hex(rawPassword);
            user.setPassword(encodedPassword);
            //插入到数据库
            baseMapper.insert(user);
            logger.info("User {} saved", user.getId());
        } catch (Exception e) {
            logger.error("Failed to save user: " + user.getId(), e);
            throw new RuntimeException("Error saving user to database", e);
        }
    }

    // 示例方法，处理用户列表等，展示异常处理和日志

    public List<User> listUsers() {
        List<User> users;
        try {
            users = baseMapper.selectList(new QueryWrapper<>());
            logger.info("User list retrieved successfully");
        } catch (Exception e) {
            logger.error("Failed to retrieve user list", e);
            throw new RuntimeException("Error fetching user list", e);
        }

        if (users == null) {
            users = new ArrayList<>();
        }

        return users;
    }

    public  User getUserByUsername(String username) {
        User user=null;
        try {
            user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
            logger.info("User {} retrieved successfully", username);
        } catch (Exception e) {
            logger.error("Failed to retrieve user {}", username, e);
            throw new RuntimeException("Error fetching user", e);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("email", email));
    }
    public Object Login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return "用户不存在";
        }
        // 使用MD5加密进行密码匹配
        String encodedPassword = DigestUtils.md5Hex(password);
        if (!encodedPassword.equals(user.getPassword())) {
            return "密码错误";
        }
        return generate(user);
    }
    public Object LoginByEmail(String email, String password) {
        User user = getUserByEmail(email);
        if (user == null) {
            return  "用户不存在";
        }
        // 使用MD5加密进行密码匹配
        String encodedPassword = DigestUtils.md5Hex(password);
        if (!encodedPassword.equals(user.getPassword())) {
            return "密码错误";
        }

        return generate(user);
    }
    public HashMap<String, Object> generate(User user){
        //设置登录
        StpUtil.login(user.getId());
        //获取登录生成的token
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        HashMap<String, Object> data = new HashMap<>();
        data.put("uid",user.getId());
        data.put("role",user.getRole());
        data.put("username",user.getUsername());
        data.put("satoken",tokenInfo.tokenValue);
        data.put("time",tokenInfo.getTokenTimeout());
        data.put("msg","登录成功");
        return data;
    }
    public User FindUserByEmail(String email){
        return baseMapper.selectOne(new QueryWrapper<User>().eq("email", email));
    }


    public void updateUser(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("用户和用户ID不能为空");
        }

        System.out.println(user.getAvatar());
        User user1 = getUserById(user.getId());
        if (user1 != null) {
            user1.setEmail(user.getEmail());
            user1.setUsername(user.getUsername());
            user1.setPhone(user.getPhone());
            user1.setAvatar(user.getAvatar());
            updateById(user1);
        } else {
            throw new IllegalArgumentException("未找到ID为: " + user.getId() + " 的用户");
        }
    }


}
