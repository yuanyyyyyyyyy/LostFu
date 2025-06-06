package com.zhulang.xfxh.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.zhulang.xfxh.config.XfXhConfig;
import com.zhulang.xfxh.dto.InteractMsg;
import com.zhulang.xfxh.dto.MsgDTO;
import com.zhulang.xfxh.dto.RecordsArray;
import com.zhulang.xfxh.listener.XfXhWebSocketListener;
import com.zhulang.xfxh.component.MemoryUserRecordSpace;
import com.zhulang.xfxh.component.XfXhStreamClient;
import com.zhulang.xfxh.serviceImpl.ChatServiceImpl;
import com.zhulang.xfxh.serviceImpl.HistoryServiceImpl;

import okhttp3.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ss
 * @create 2023-09-15 0:42
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private XfXhConfig xfXhConfig;

    @Resource
    private XfXhStreamClient xfXhStreamClient;

    @Resource
    private MemoryUserRecordSpace memoryUserRecordSpace;
    @Resource
    HistoryServiceImpl service;
    @Resource
    ChatServiceImpl chatService;

    // 使用 id 作为唯一用户的标识（区分不同用户）
    @GetMapping("/sendQuestion")
    public String question(@RequestParam("uid") Long id, @RequestParam("question") String question) throws InterruptedException {
        return chatService.SendQuestion(id,question);
    }

    // 测试使用，查看内存空间中所有的用户记录信息
    @GetMapping("/spaceInfo")
    public List<JSONObject> spaceInfo(){
        ConcurrentHashMap<Long, RecordsArray> userRecordMap = memoryUserRecordSpace.getUserRecordMap();
        ArrayList<JSONObject> infoList = new ArrayList<>(userRecordMap.size());
        for (Map.Entry<Long, RecordsArray> entry : userRecordMap.entrySet()) {
            RecordsArray recordsArray = entry.getValue();
            JSONObject data = new JSONObject();
            data.put("id",entry.getKey());
            data.put("allInteractMsg",recordsArray.getAllInteractMsg());
            data.put("status",recordsArray.getStatus());
            data.put("lock",recordsArray.isLock());
            infoList.add(data);
        }

        return infoList;
    }
    //结束聊天
    @GetMapping("/stopChat")
    public String StopChat(@RequestParam("uid") Long id){
        service.clearSessionById(id);
        return null;
    }
    //历史记录插入新的聊天
    @GetMapping("/hisQuestion")
    public String hisQuestion(@RequestParam("uid") Long id, @RequestParam("question") String question,@RequestParam("sessionId") String sessionId) throws InterruptedException {
    return     chatService.sendQuestionByHistory(id, question, sessionId);
    }
}
