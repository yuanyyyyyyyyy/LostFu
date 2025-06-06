package com.zhulang.xfxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 权限表实体类
public class Auth {
    private Integer id;
    private String roleName;
    // 构造器、getter和setter省略
}