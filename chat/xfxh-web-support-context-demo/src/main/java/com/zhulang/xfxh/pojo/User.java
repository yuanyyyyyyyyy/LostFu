package com.zhulang.xfxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Integer role;
    private String phone;
    private String email;
    private BigDecimal balance;
    private String avatar;

    // 构造器、getter和setter省略
}