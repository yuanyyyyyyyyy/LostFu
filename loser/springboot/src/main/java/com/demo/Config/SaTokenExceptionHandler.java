package com.demo.Config;



import cn.dev33.satoken.exception.*;

import com.demo.Response.response;
import com.demo.Response.setRe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理（satoken框架）
 *
 * @Author Lizhou
 */
@RestControllerAdvice
@Slf4j
public class SaTokenExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public response handlerNotLoginException(NotLoginException nle) {
        // 不同异常返回不同状态码
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供Token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "未提供有效的Token";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "登录信息已过期，请重新登录";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "您的账户已在另一台设备上登录，如非本人操作，请立即修改密码";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "已被系统强制下线";
        } else {
            message = "当前会话未登录";
        }
        // 返回给前端
        log.info("出现未登录异常：{}",message);
        return setRe.setNotLoginOrTokenNull(null);
    }

    @ExceptionHandler
    public response handlerNotRoleException(NotRoleException e) {//查无此人
        return setRe.setUserNull(null);
    }

    @ExceptionHandler
    public response handlerNotPermissionException(NotPermissionException e) {//无权限
        return setRe.setAuthFail(null);
    }

//    @ExceptionHandler
//    public response handlerDisableLoginException(DisableLoginException e) {
//        return RES.no(401, "账户被封禁：" + e.getDisableTime() + "秒后解封");
//    }
//
//    @ExceptionHandler
//    public response handlerNotSafeException(NotSafeException e) {
//        return RES.no(401, "二级认证异常：" + e.getMessage());
//    }
}
