package com.zhulang.xfxh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhulang.xfxh.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    // 自定义方法
}