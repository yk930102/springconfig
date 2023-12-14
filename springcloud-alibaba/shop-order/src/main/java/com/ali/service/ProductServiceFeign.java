package com.ali.service;

import com.ali.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//value用于指定调用nacos下哪个微服务
//fallback用于指定容错类
//注意: fallback和fallbackFactory只能使用其中一种方式
@FeignClient(value = "service-product"
//        fallback = ProductServiceFallBack.class
//        fallbackFactory = ProductServiceFallBackFactory.class
)
public interface ProductServiceFeign {
    //@FeignClient的value + @RequestMapping的value值 其实就是完成的请求地址
    //"http://service-product/product/" + pid
    @GetMapping(value = "/product/{pid}")
    Product findByPid(@PathVariable("pid") Integer pid);

    //减库存
    @RequestMapping("/product/reduceInventory")
    void reduceInventory(@RequestParam("pid") Integer pid, @RequestParam("num")
            int num);
}
