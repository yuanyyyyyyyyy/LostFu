package com.demo.Service;

import com.alipay.api.AlipayApiException;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

// 支付服务接口，定义支付相关功能
public interface PayService {
    // 创建支付订单（返回支付宝表单HTML）
    String createPayOrder(String orderNo, BigDecimal amount, String subject) throws AlipayApiException;

    // 验证支付回调通知
    boolean verifyPayCallback(HttpServletRequest request);
}