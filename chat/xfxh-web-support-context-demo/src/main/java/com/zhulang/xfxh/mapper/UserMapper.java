package com.zhulang.xfxh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhulang.xfxh.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 这里可以添加自定义的方法，MyBatis-Plus 已经提供了很多基本的 CRUD 方法
}