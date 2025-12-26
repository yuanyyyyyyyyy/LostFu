package com.demo.Controller;

import com.alipay.api.AlipayApiException;
import com.demo.Service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/pay")
public class PayController {

    @Autowired
    private PayService payService;

    // 创建支付订单
    @PostMapping("/create")
    public String createPay(
            @RequestParam String orderNo,
            @RequestParam BigDecimal amount,
            @RequestParam String subject
    ) throws AlipayApiException {
        return payService.createPayOrder(orderNo, amount, subject);
    }

    // 支付异步通知接口
    @PostMapping("/notify")
    public String payNotify(HttpServletRequest request) {
        boolean success = payService.verifyPayCallback(request);
        return success ? "success" : "fail"; // 支付宝要求返回"success"或"fail"
    }
}