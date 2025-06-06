package com.zhulang.xfxh.hanlder;


import com.zhulang.xfxh.Reponse.ResultData;
import com.zhulang.xfxh.Reponse.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
//全局异常处理器
// 统一处理异常 简化代码
// 学习-》 https://blog.csdn.net/asxyxxx/article/details/121806762
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)//处理的异常类型，当然也可以自定义异常类型，以及异常处理类，比如一些权限异常等等
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e){
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return ResultData.fail(ReturnCode.RC500.getCode(), e.getMessage());
    }
}

