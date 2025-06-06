package com.zhulang.xfxh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhulang.xfxh.pojo.Auth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper extends BaseMapper<Auth> {
    // 自定义方法
}