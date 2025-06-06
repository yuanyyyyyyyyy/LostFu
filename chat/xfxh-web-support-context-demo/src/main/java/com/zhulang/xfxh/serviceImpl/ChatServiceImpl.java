package com.zhulang.xfxh.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.zhulang.xfxh.component.MemoryUserRecordSpace;
import com.zhulang.xfxh.component.XfXhStreamClient;
import com.zhulang.xfxh.config.XfXhConfig;
import com.zhulang.xfxh.dto.InteractMsg;
import com.zhulang.xfxh.dto.MsgDTO;
import com.zhulang.xfxh.listener.XfXhWebSocketListener;
import okhttp3.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Service
public class ChatServiceImpl {
    @Resource
    private XfXhConfig xfXhConfig;

    @Resource
    private XfXhStreamClient xfXhStreamClient;

    @Resource
    private MemoryUserRecordSpace memoryUserRecordSpace;

    @Resource
    private HistoryServiceImpl service;

    private static final int MAX_RESPONSE_TIME_MULTIPLIER = 5;

    private boolean acquireLockAndToken(Long id) {
        return memoryUserRecordSpace.tryLock(id) && xfXhStreamClient.operateToken(XfXhStreamClient.GET_TOKEN_STATUS);
    }

    private void releaseLockAndToken(Long id) {
        memoryUserRecordSpace.unLock(id);
        xfXhStreamClient.operateToken(XfXhStreamClient.BACK_TOKEN_STATUS);
    }

    private String sendQuestion(Long id, String question, String sessionId) throws InterruptedException {
        if (StrUtil.isBlank(question)) {
            return "无效问题，请重新输入";
        }
        if (!acquireLockAndToken(id)) {
            return "正在处理上次问题，请稍后再试";
        }
        try {
            MsgDTO msgDTO = MsgDTO.createUserMsg(question);
            XfXhWebSocketListener listener = new XfXhWebSocketListener();
            List<MsgDTO> msgList = memoryUserRecordSpace.getAllInteractMsg(id);
            msgList.add(msgDTO);
            WebSocket webSocket = xfXhStreamClient.sendMsg(UUID.randomUUID().toString().substring(0, 10), msgList, listener);

            if (webSocket == null) {
                return "系统内部错误，请联系管理员";
            }

            int maxCount = xfXhConfig.getMaxResponseTime() * MAX_RESPONSE_TIME_MULTIPLIER;
            for (int count = 0; count < maxCount; count++) {
                TimeUnit.MILLISECONDS.sleep(200);
                if (listener.isWsCloseFlag()) {
                    break;
                }
            }

            if (!listener.isWsCloseFlag()) {
                return "大模型响应超时，请联系管理员";
            }
            String answer = listener.getAnswer().toString();
            memoryUserRecordSpace.storeInteractMsg(id, new InteractMsg(MsgDTO.createUserMsg(question), MsgDTO.createAssistantMsg(answer)));
            if (sessionId != null) {
                service.insertBySessionId(id, sessionId, answer, question);
            } else {
                service.insertChatRecord(id, answer, question);
            }

            return answer;
        } finally {
            releaseLockAndToken(id);
        }
    }

    public String SendQuestion(Long id, String question) throws InterruptedException {
        return sendQuestion(id, question, null);
    }

    public String sendQuestionByHistory(Long id, String question, String sessionId) throws InterruptedException {
        return sendQuestion(id, question, sessionId);
    }
}
