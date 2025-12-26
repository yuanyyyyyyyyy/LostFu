package com.demo.Service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.demo.Config.AlipayConfig;
import com.demo.Service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private AlipayConfig alipayConfig;

    @Override
    public String createPayOrder(String orderNo, BigDecimal amount, String subject) throws AlipayApiException {
        // 初始化支付宝客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),
                alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(),
                alipayConfig.getFormat(),
                alipayConfig.getCharset(),
                alipayConfig.getPublicKey(),
                alipayConfig.getSignType()
        );

        // 创建支付请求
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 支付成功后回调地址（需替换为你的前端回调页面）
        request.setReturnUrl("http://localhost:40001/pay/success");
        // 异步通知地址（需替换为你的后端接口）
        request.setNotifyUrl(" http://v64bc46f.natappfree.cc/api/pay/notify");

        // 组装请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", orderNo); // 订单号
        params.put("total_amount", amount); // 金额（单位：元）
        params.put("subject", subject); // 订单标题（如：失物招领悬赏金）
        params.put("product_code", "FAST_INSTANT_TRADE_PAY"); // 固定值

        request.setBizContent(new com.alibaba.fastjson.JSONObject(params).toString());
        // 生成支付表单HTML
        return alipayClient.pageExecute(request).getBody();
    }

    @Override
    public boolean verifyPayCallback(HttpServletRequest request) {
        // 此处省略回调验证逻辑（参考支付宝官方文档实现签名验证）
        // 验证通过后更新订单状态
        return true;
    }
}