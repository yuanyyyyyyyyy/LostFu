package com.zhulang.xfxh.controller;

import com.zhulang.xfxh.pojo.History;
import com.zhulang.xfxh.serviceImpl.HistoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Resource
    HistoryServiceImpl service;
    //获取历史记录
    @GetMapping("/HistoryList")
    public List<History> HistoryList(@RequestParam("uid") Long uid) {
        log.info("{}用户获取历史记录",uid);
        return service.findChatRecords(uid, null);
    }
    //获取单次聊天记录详情
    @GetMapping("/HistoryDetail")
    public List<History> HistoryDetail(@RequestParam("sessionId") String sessionId){
        log.info("用户获取{}会话详细数据",sessionId);
        return service.findChatRecords(null,sessionId);
    }
}
