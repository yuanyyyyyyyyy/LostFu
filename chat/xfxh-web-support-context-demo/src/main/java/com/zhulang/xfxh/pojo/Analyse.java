package com.zhulang.xfxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 用户搜题记录表实体类
public class Analyse {
    private Long id;
    private Long uid;
    private Integer correct;
    private Integer mistake;
    private String photo;
    private String date;

    // 构造器、getter和setter省略
}