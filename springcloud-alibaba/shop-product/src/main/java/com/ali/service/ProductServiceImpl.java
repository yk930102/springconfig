package com.ali.service;

import com.ali.dao.ProductDao;
import com.ali.entity.Product;
import com.ali.service.ProductService;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product findByPid(Integer pid) {
        return productDao.findById(pid).get();
    }

    @Override
    public void reduceInventory(Integer pid, int number) {
        System.out.println(RootContext.getXID());
        Product product = productDao.findById(pid).get();
        if (product.getStock() < number) {
            throw new RuntimeException("库存不足");
        }
//        int i = 1 / 0;
        product.setStock(product.getStock() - number);//减库存
        productDao.save(product);
    }
}
