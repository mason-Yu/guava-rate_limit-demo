package com.guava.example.demo.controller;

import com.guava.example.demo.common.CommonResult;
import com.guava.example.demo.common.ResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public ResponseDTO helloWorld(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(CommonResult.OK.getCode());
        responseDTO.setMsg(CommonResult.OK.getMsg());
        return responseDTO;
    }
}
