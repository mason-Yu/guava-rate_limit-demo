package com.guava.example.demo.interceptor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.guava.example.demo.common.CommonResult;
import com.guava.example.demo.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Slf4j
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       CommonResult result;
       try {
            result = preFilter(request);
       }catch (Exception ex){
        log.error("preFilter catch an Exception", ex);
        result = CommonResult.SERVER_ERROR;
       }
       if(CommonResult.OK == result){
           return true;
       }
       /* 失败直接返回结果 */
       handleResponse(result, response);
       return false;
    }

    abstract CommonResult preFilter(HttpServletRequest request);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    }

    private void handleResponse(CommonResult result, HttpServletResponse response){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(result.getCode());
        responseDTO.setMsg(result.getMsg());
        responseDTO.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(responseDTO));
        }catch (Exception ex){
            log.error("handlerResponse catch an Exception", ex);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
