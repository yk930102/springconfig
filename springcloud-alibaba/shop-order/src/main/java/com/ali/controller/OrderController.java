package com.ali.controller;

import com.ali.entity.Order;
import com.ali.entity.Product;
import com.ali.service.OrderService;
import com.ali.service.ProductServiceFeign;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductServiceFeign productServiceFeign;
    @Autowired
    private DiscoveryClient discoveryClient;
    //准备买1件商品
    @GetMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) {
        log.info(">>客户下单，这时候要调用商品微服务查询商品信息");
        //v5
        Product product = productServiceFeign.findByPid(pid);
        if (product.getPid() == -1) {
            Order order = new Order();
            order.setPname("下单失败");
            return order;
        }
        //v4
//        String url = "service-product";
        //v3
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
//        int index = new Random().nextInt(instances.size());
//        ServiceInstance serviceInstance = instances.get(index);
//        String url = serviceInstance.getHost() + ":" +
//                serviceInstance.getPort();
//        log.info(">>从nacos中获取到的微服务地址为:" + url);
        //v2
        //从nacos中获取服务地址
//        ServiceInstance serviceInstance =
//                discoveryClient.getInstances("service-product").get(0);
//        String url = serviceInstance.getHost() + ":" +
//                serviceInstance.getPort();
//        log.info(">>从nacos中获取到的微服务地址为:" + url);
//        Product product = restTemplate.getForObject(
//                "http://" + url + "/product/" + pid, Product.class);
        //v1
//        //通过restTemplate调用商品微服务
//        Product product = restTemplate.getForObject(
//                "http://localhost:8081/product/" + pid, Product.class);
        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
        Order order = new Order();
        order.setUid(1);
        order.setUsername("测试用户");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderService.save(order);
        return order;
    }
    @PostConstruct
    public void init(){
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule flowRule = new FlowRule();
//        FlowRuleManager.loadRules(rules);

//        SphU.entry("");
    }
}
