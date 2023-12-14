package com.ali.controller;

import com.ali.entity.Order;
import com.ali.entity.Product;
import com.ali.service.OrderService;
import com.ali.service.ProductServiceFeign;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController2 {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductServiceFeign productServiceFeign;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    //准备买1件商品
    @GetMapping("/order2/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) {
        log.info(">>客户下单,这时候要调用商品微服务查询商品信息");
        //通过fegin调用商品微服务
        Product product = productServiceFeign.findByPid(pid);
        if (product == null) {
            Order order = new Order();
            order.setPname("下单失败");
            return order;
        }
        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
        Order order = new Order();
        order.setUid(1);
        order.setUsername("测试用户");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderService.save(order);
        //下单成功之后,将消息放到mq中
        rocketMQTemplate.convertAndSend("order-topic", order);
        return order;
    }
}
