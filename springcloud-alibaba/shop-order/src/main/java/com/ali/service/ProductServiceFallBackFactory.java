package com.ali.service;

import com.ali.entity.Product;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

//@Component
public class ProductServiceFallBackFactory implements
        FallbackFactory<ProductServiceFeign> {
    @Override
    public ProductServiceFeign create(Throwable throwable) {
        return new ProductServiceFeign() {
            @Override
            public Product findByPid(Integer pid) {
                throwable.printStackTrace();
                Product product = new Product();
                product.setPid(-1);
                return product;
            }

            @Override
            public void reduceInventory(Integer pid, int num) {

            }
        };
    }
}
