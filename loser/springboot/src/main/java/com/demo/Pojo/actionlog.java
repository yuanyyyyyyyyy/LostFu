package com.demo.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("actionlog")
public class actionlog {
    private int id;
    private int uid;
    private String date;
    private String type;
}
