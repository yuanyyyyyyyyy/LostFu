package com.zhulang.xfxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 文件转换记录实体类
public class Convert {
    private Long id;
    private Long uid;
    private String original;
    private String conversion;
    private String date;

    // 构造器、getter和setter省略
}
