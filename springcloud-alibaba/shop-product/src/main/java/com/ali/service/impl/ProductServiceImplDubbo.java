package com.ali.service.impl;

import com.ali.dao.ProductDao;
import com.ali.entity.Product;
import com.ali.entity.service.ProductService2;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductServiceImplDubbo implements ProductService2 {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product findByPid(Integer pid) {
        return productDao.findById(pid).get();
    }

}
