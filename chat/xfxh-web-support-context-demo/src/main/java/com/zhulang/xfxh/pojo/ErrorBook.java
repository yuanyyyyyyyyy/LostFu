package com.zhulang.xfxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 用户错题本实体类
public class ErrorBook {
    private Long id;
    private Long aid;
    private String question;
    private String answer;

    // 构造器、getter和setter省略
}

