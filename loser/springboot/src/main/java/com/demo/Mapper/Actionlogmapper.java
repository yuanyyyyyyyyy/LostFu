package com.demo.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.demo.Pojo.actionlog;
import com.demo.Pojo.post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Actionlogmapper extends BaseMapper<actionlog> {
}
