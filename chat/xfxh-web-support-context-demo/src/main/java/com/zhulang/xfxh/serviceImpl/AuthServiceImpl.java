package com.zhulang.xfxh.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhulang.xfxh.mapper.AuthMapper;
import com.zhulang.xfxh.pojo.Auth;
import com.zhulang.xfxh.service.AuthService;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements AuthService {

    /**
     * 根据角色名查询权限
     * 
     * @param roleName 角色名称
     * @return 返回匹配该角色名的权限列表
     */

}
