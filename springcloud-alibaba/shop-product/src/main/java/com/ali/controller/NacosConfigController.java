package com.ali.controller;

import com.ali.entity.Product;
import com.ali.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//只需要在需要动态读取配置的类上添加此注解就可以
@Slf4j
public class NacosConfigController {
//    @Value("${config.appName}")
    private String appName;

//    @Value("${config.env}")
    private String env;

    @Autowired
    private ProductService productService;

    //2 注解方式
    @GetMapping("/nacos-config-test2")
    public String nacosConfingTest2() {
        Product product = productService.findByPid(1);
        log.info("nacos-config-test2");
        return appName;
    }

    //3 同一微服务的不同环境下共享配置
    @GetMapping("/nacos-config-test3")
    public String nacosConfingTest3() {
        Product product = productService.findByPid(2);
        log.info("product{}",product);
        return env;
    }
}
