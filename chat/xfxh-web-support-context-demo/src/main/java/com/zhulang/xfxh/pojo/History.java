package com.zhulang.xfxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 用户问答记录实体类
public class History {
    private Long id;
    private Long uid;
    private String rootContent;
    private String content;
    private String sessionId; // 聊天会话ID
    private String date;
    private  String title;//聊天标题

    // 构造器、getter和setter省略

}