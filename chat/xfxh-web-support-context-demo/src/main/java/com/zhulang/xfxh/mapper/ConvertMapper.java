package com.zhulang.xfxh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhulang.xfxh.pojo.form;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConvertMapper extends BaseMapper<form> {
    // 自定义方法
}