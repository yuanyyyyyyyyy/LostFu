package com.zhulang.xfxh.Reponse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


//统一处理返回结果，如果在controller一个个写返回结果很麻烦
//  学习=》https://blog.csdn.net/asxyxxx/article/details/121806762
@RestControllerAdvice(basePackages = "com.zhulang.xfxh")
public class ResponseAdvice implements ResponseBodyAdvice <Object> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        // 设置ObjectMapper的编码格式为UTF-8
        this.objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(body instanceof String){	// 如果Controller直接返回String的话，SpringBoot是直接返回，故我们需要手动转换成json。

            return objectMapper.writeValueAsString(ResultData.success(body));
        }
        if(body instanceof ResultData){	// 如果返回的结果是ResultData对象，直接返回即可。
            return body;
        }

        return ResultData.success(body);
    }
}
