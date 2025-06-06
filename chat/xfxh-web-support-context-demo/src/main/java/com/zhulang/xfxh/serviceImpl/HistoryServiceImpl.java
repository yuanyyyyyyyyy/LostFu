package com.zhulang.xfxh.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhulang.xfxh.dto.InteractMsg;
import com.zhulang.xfxh.dto.MsgDTO;
import com.zhulang.xfxh.dto.RecordsArray;
import com.zhulang.xfxh.mapper.HistoryMapper;
import com.zhulang.xfxh.pojo.History;
import com.zhulang.xfxh.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    private final ConcurrentHashMap<Long, String> userSession = new ConcurrentHashMap<>();

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String getSessionId(Long uid) {
        return userSession.computeIfAbsent(uid, k -> UUID.randomUUID().toString());
    }

    public List<History> findChatRecords(Long uid, String sessionId) {
        LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<>();
        // 根据uid和sessionId的组合条件进行查询
      if (uid != null) {
          queryWrapper.select(History::getDate, History::getTitle, History::getSessionId);
            queryWrapper.eq(History::getUid, uid).groupBy(History::getSessionId).orderByAsc(History::getDate);
        }
      if (sessionId != null) {
          queryWrapper.select(History::getDate,
                  History::getTitle, History::getSessionId,
                  History::getContent,History::getRootContent
          );
            queryWrapper.eq(History::getSessionId, sessionId).orderByAsc(History::getDate);
        }
        // 执行查询并返回结果
        return list(queryWrapper);
    }

    public void insertChatRecord(Long uid, String rootContent, String content) {
        String sessionId = getSessionId(uid);
        String date = formatDate(LocalDate.now());
        History history = new History();
        history.setTitle(content);
        history.setUid(uid);
        history.setDate(date);
        history.setRootContent(rootContent);
        history.setContent(content);
        history.setSessionId(sessionId);
        save(history);
    }

    public void clearSessionById(Long id) {
        userSession.remove(id);
    }

    public void insertBySessionId(Long uid, String sessionId, String rootContent, String content) {
        History history = new History();
        history.setTitle(content);
        history.setUid(uid);
        history.setDate(formatDate(LocalDate.now()));
        history.setRootContent(rootContent);
        history.setContent(content);
        history.setSessionId(sessionId);
      save(history);
    }
}
