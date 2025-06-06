package com.zhulang.xfxh.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    private static final Map<String, VerificationCode> verificationCodes = new HashMap<>();

    @Autowired
    private JavaMailSender mailSender;//一定要用@Autowired

    @Value("${spring.mail.username}")
    private  String FROM_ADDRESS ; // 发送者邮箱
    private  String SUBJECT = "邮箱验证码"; // 邮件主题

    public void sendVerificationCode(String email) {
        //TODO 这里需要修改（添加判断改邮箱是否注册过，只有注册过才能发送）
        String code = generateVerificationCode();
        VerificationCode verificationCode = new VerificationCode(code, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)); // 有效期5分钟
        verificationCodes.put(email, verificationCode);
        sendEmail(email, code); // 发送邮件
    }

    public boolean verifyCode(String email, String code) {
        VerificationCode verificationCode = verificationCodes.get(email);
        if (verificationCode != null && verificationCode.getCode().equals(code)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime <= verificationCode.getExpiryTime()) {
                // 验证码正确且未过期
                verificationCodes.remove(email); // 验证码一旦使用，立即删除
                return true;
            }
        }
        return false;
    }

    private void sendEmail(String toAddress, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(FROM_ADDRESS);
        mailMessage.setTo(toAddress);
        mailMessage.setSubject(SUBJECT);
        mailMessage.setText("您的验证码是：" + code);
        mailSender.send(mailMessage);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000)); // 生成一个六位数的随机验证码
    }

    private static class VerificationCode {
        private final String code;
        private final long expiryTime;

        public VerificationCode(String code, long expiryTime) {
            this.code = code;
            this.expiryTime = expiryTime;
        }

        public String getCode() {
            return code;
        }

        public long getExpiryTime() {
            return expiryTime;
        }
    }
}
