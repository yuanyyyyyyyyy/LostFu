package com.zhulang.xfxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 菜单表实体类
public class Menu {
    private Integer id;
    private String name;
    private String url;
    private String icon;
    private String ico;
    private Integer roleId;

    // 构造器、getter和setter省略
}