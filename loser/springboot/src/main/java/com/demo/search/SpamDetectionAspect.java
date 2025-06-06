package com.demo.search;

import com.demo.Pojo.post;
import com.demo.Response.response;
import com.demo.Response.setRe;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpamDetectionAspect {
    private final SpamFilter spamFilter;

    @Autowired
    public SpamDetectionAspect(SpamFilter spamFilter) {
        this.spamFilter = spamFilter;
    }
    // 定义切入点，匹配所有带有 @CheckForSpam 注解的方法
    @Pointcut("@annotation(CheckForSpam)")
    public void spamCheck() {
    }


    @Around("spamCheck() && args(post)")
    public Object checkForSpam(ProceedingJoinPoint joinPoint, post post) throws Throwable {
        if (spamFilter.isSpamDetected(post.getDescribe())) {
            return setRe.setBad(null); // 如果检测到垃圾内容，返回一个适当的响应
        }

        // 如果没有检测到垃圾内容，继续执行被拦截的方法
        return joinPoint.proceed();
    }
}
