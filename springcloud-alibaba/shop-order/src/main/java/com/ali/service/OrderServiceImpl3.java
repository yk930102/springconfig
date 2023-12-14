package com.ali.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl3 {
    int i = 1;
//    @SentinelResource("message")
    @SentinelResource(
            value = "message",
            blockHandler = "blockHandler",//指定发生BlockException时进入的方法
            fallback = "fallback")//指定发生Throwable时进入的方法
    public String message() {
        System.out.println("message");
        i++;
        //异常比例为0.333
        if (i % 3 == 0){
            throw new RuntimeException();
        }
        return "message";
    }


    //BlockException时进入的方法
    public String blockHandler(BlockException ex) {
        log.error("blockHandler{}", ex);
        return "接口被限流或者降级了...";
    }
    //Throwable时进入的方法
    public String fallback(Throwable throwable) {
        log.error("{}", throwable);
        return "接口发生异常了...";
    }
}
