package com.ali.controller;

import com.ali.entity.Order;
import com.ali.entity.Product;
import com.ali.entity.service.ProductService2;
import com.ali.service.OrderServiceImpl5;
import com.alibaba.fastjson.JSON;
import io.seata.core.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController5 {
    @Autowired
    private OrderServiceImpl5 orderService;

    //引用服务
    @Reference
    private ProductService2 productService2;
    //下单
    @RequestMapping("/order5/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) throws TransactionException {
        log.info("接收到{}号商品的下单请求,接下来调用商品微服务查询此商品信息", pid);
        return orderService.createOrder(pid);
    }
    //下单
    @RequestMapping("/order5/prod2/{pid}")
    public Product prod2(@PathVariable("pid") Integer pid) {
        log.info("接收到{}号商品的下单请求,接下来调用商品微服务查询此商品信息", pid);
        //调用商品微服务,查询商品信息
        Product product = productService2.findByPid(pid);
        log.info("查询到{}号商品的信息,内容是:{}", pid, JSON.toJSONString(product));
        return product;
    }
}