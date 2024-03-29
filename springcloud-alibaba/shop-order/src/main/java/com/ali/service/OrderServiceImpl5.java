package com.ali.service;

import com.ali.dao.OrderDao;
import com.ali.entity.Order;
import com.ali.entity.Product;
import com.alibaba.fastjson.JSON;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl5 {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductServiceFeign productServiceFeign;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GlobalTransactional
    public Order createOrder(Integer pid) throws TransactionException {
        System.out.println(RootContext.getXID());
        //1 调用商品微服务,查询商品信息
        Product product = productServiceFeign.findByPid(pid);
        log.info("查询到{}号商品的信息,内容是:{}", pid, JSON.toJSONString(product));
        //2 下单(创建订单)
        Order order = new Order();
        order.setUid(1);
        order.setUsername("测试用户");
        order.setPid(pid);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderDao.save(order);
        log.info("创建订单成功,订单信息为{}", JSON.toJSONString(order));
        //3 扣库存
        productServiceFeign.reduceInventory(pid, order.getNumber());
        //4 向mq中投递一个下单成功的消息
        try {
            rocketMQTemplate.convertAndSend("order-topic", order);
        }catch (Exception e){
            e.printStackTrace();
//            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }

        return order;
    }
}
