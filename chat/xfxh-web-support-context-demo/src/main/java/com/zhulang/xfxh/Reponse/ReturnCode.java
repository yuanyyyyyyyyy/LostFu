package com.zhulang.xfxh.Reponse;
//返回状态码
public enum ReturnCode {
    RC200(2000, "请求成功"),
    RC500(5000, "请求失败");

    // 自定义状态码
    private final int code;

    // 自定义描述
    private final String msg;

    ReturnCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
