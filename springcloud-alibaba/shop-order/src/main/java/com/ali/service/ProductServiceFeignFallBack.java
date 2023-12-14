package com.ali.service;

import com.ali.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//容错类要求必须实现被容错的接口,并为每个方法实现容错方案
//@Component
@Slf4j
public class ProductServiceFeignFallBack implements ProductServiceFeign {
    @Override
    public Product findByPid(Integer pid) {
        Product product = new Product();
        product.setPid(-1);
        return product;
    }

    @Override
    public void reduceInventory(Integer pid, int num) {

    }
}
