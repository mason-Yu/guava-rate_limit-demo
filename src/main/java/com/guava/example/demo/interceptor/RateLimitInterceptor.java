package com.guava.example.demo.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.guava.example.demo.common.CommonResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("rateLimitInterceptor")
public class RateLimitInterceptor extends AbstractInterceptor {

        /**
      * 单机全局限流器(限制QPS为1)
      */
        private static final RateLimiter rateLimiter = RateLimiter.create(2);
        @Override
        protected CommonResult preFilter(HttpServletRequest request) {
            if (!rateLimiter.tryAcquire()) {
                System.out.println("限流中......");
                return CommonResult.RATE_LIMIT;
            }
            System.out.println("请求成功");
            return CommonResult.OK;
        }
}
