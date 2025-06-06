package com.zhulang.xfxh.config;


import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author ss
 * @date 2022/9/23 16:49
 * @description
 */
@SpringBootConfiguration
@EnableWebMvc
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能
     * 如果在高版本 SpringBoot (≥2.6.x) 下注册拦截器失效，则需要额外添加 @EnableWebMvc 注解才可以使用
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 登录认证 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**", "/user/login", r -> StpUtil.checkLogin());
//                    // 角色认证 -- 拦截以 admin 开头的路由，必须具备 admin 角色或者 super-admin 角色才可以通过认证
//                    SaRouter.match("/admin/**", r -> StpUtil.checkRoleOr("admin", "super-admin"));
//                    // 权限认证 -- 不同模块认证不同权限
//                    SaRouter.match("/user/**", r -> StpUtil.checkRole("user"));
//                    SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));

                }).isAnnotation(true))
                //拦截所有接口
                .addPathPatterns("/**")
                //不拦截
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/verifyCode")
                .excludePathPatterns("/user/sendCode")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/images/**.png");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\");

    }

}

