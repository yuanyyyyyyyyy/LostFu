package com.demo.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@TableName("auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class auth {
    private int id;
    private String auth_name;
}
