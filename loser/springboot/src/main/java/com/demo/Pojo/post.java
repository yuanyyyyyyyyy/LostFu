package com.demo.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("post")
public class post {
    private int id;
    private String username;
    private String tel;
    private String describe;
    private String Picaddr;
    private String date;
    private String type;
    private int crscore;
}
