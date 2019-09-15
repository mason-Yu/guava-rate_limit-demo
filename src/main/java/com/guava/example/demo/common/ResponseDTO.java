package com.guava.example.demo.common;

import lombok.Data;

@Data
public class ResponseDTO {
    private int code;
    private String msg;
    private int status;
}
