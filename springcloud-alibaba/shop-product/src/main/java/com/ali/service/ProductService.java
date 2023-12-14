package com.ali.service;

import com.ali.entity.Product;


public interface ProductService {
    Product findByPid(Integer pid);
    void reduceInventory(Integer pid,int num);
}
