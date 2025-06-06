package com.zhulang.xfxh.listener;

import com.alibaba.fastjson.JSONObject;
import com.zhulang.xfxh.dto.MsgDTO;
import com.zhulang.xfxh.dto.ResponseDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

/**
 * @author ss
 * @create 2023-09-15 1:11
 */
@Data
@Slf4j
public class XfXhWebSocketListener extends WebSocketListener {
    private StringBuilder answer = new StringBuilder();

    private boolean wsCloseFlag = false;

    /**
     * 当WebSocket连接打开时调用此方法。
     * 此方法重写了父类的onOpen方法，以提供特定于子类的实现。
     * 在这里，它简单地调用了超类的onOpen方法，传递了WebSocket连接对象和响应对象。
     *
     * @param webSocket WebSocket连接对象，用于与客户端进行通信。
     * @param response 响应对象，可能包含关于连接初始化请求的详细信息。
     */
    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
    }


    /**
     * 当接收到WebSocket消息时的处理方法。
     *
     * @param webSocket 当前的WebSocket连接对象。
     * @param text 接收到的文本消息。
     */
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        // 解析接收到的JSON字符串为ResponseDTO对象。
        ResponseDTO responseData = JSONObject.parseObject(text, ResponseDTO.class);
        // 检查响应码，如果不为0，则表示处理过程中发生了错误。
        if (responseData.getHeader().getCode() != 0) {
            // 记录错误信息到日志。
            // 日志记录
            log.error("发生错误，错误码为：" + responseData.getHeader().getCode() + "; " + "信息：" + responseData.getHeader().getMessage());
            // 设置回答信息为错误提示，并关闭WebSocket连接。
            // 记录信息
            this.answer = new StringBuilder("大模型响应错误，请稍后再试");
            wsCloseFlag = true;
            return;
        }
        // 遍历响应中的消息列表，将消息内容拼接到回答中。
        // 将回答进行拼接
        for (MsgDTO msgDTO : responseData.getPayload().getChoices().getText()) {
            this.answer.append(msgDTO.getContent());
        }
        // 如果响应状态为2，表示处理完成，关闭WebSocket连接。
        // 对最后一个文本结果进行处理
        if (2 == responseData.getHeader().getStatus()) {
            wsCloseFlag = true;
        }
    }


    /**
     * 当WebSocket连接失败时的回调方法。
     * <p>
     * 该方法重写了父类的onFailure方法，以处理WebSocket连接失败的情况。当WebSocket连接尝试失败时，此方法将被调用。
     * 这里可以添加自定义的失败处理逻辑，例如重试连接、记录日志等。
     *
     * @param webSocket 当前的WebSocket对象，用于建立连接或发送数据。
     * @param t 引发失败的异常对象，提供了关于失败原因的详细信息。
     * @param response 若连接已建立，则为建立连接时的响应对象；否则为null。
     *                  可以通过该响应对象获取服务器返回的详细信息，例如状态码、头信息等。
     */
    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    /**
     * 当WebSocket连接关闭时调用此方法。
     *
     * 重写此方法以提供连接关闭时的自定义处理逻辑。在此方法中，我们调用了超类的onClosed方法，
     * 以确保任何超类实现的逻辑也被执行。
     *
     * @param webSocket 关闭的WebSocket连接的实例。
     * @param code 连接关闭的代码，表示关闭的原因。
     * @param reason 连接关闭的文本原因。
     */
    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }
}
