package com.demo.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.demo.Pojo.user;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Usermappper extends BaseMapper<user> {
}
