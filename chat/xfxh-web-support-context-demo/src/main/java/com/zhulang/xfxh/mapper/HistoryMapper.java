package com.zhulang.xfxh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhulang.xfxh.pojo.History;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryMapper extends BaseMapper<History> {
    // 自定义方法
}