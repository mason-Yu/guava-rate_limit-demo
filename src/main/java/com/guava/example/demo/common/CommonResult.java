package com.guava.example.demo.common;

public enum CommonResult {
    OK(200, "请求成功"),
    RATE_LIMIT(401, "访问次数受限"),
    SERVER_ERROR(500, "服务器错误"),
    QUERY_USER_FAILED(601, "查询用户失败");
    private int code;
    private String msg;
    CommonResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
