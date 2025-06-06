package com.demo.Response;

import com.demo.Pojo.user;
import lombok.Data;

@Data
public class response {
    private int code;//
    private String message;
    private Object data;

    public response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
