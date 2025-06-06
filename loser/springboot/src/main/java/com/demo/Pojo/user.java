package com.demo.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class user {
    private  int id;
    private  int Infoid;
    private String username;
    private String password;
    private String tel;
    private int crscore;
    private int auth;
    private int calmday;
}
