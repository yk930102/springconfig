package com.ali.service;

import com.ali.dao.UserDao;
import com.ali.entity.Order;
import com.ali.entity.User;
import com.ali.util.SmsUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

//发送短信的服务
@Slf4j
@Service("shopSmsService")
@RocketMQMessageListener(
        consumerGroup = "shop-user",
        topic = "order-topic",
        consumeMode = ConsumeMode.CONCURRENTLY,//消费模式
        messageModel = MessageModel.CLUSTERING//消息模式 集群、广播
        )
public class SmsService implements RocketMQListener<Order> {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MailService mailService;
    @Override
    public void onMessage(Order order) {
        log.info("收到一个订单信息{},接下来发送短信", JSON.toJSONString(order));
        //根据uid 获取手机号
        User user = userDao.findById(order.getUid()).get();
        //生成验证码
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(new Random().nextInt(9) + 1);
        }
        String smsCode = builder.toString();
        Param param = new Param(smsCode);
        try {
            //发送短信 {"code":"123456"}
//            SmsUtil.sendSms(user.getTelephone(), "个人网站", "SMS_175051154",
//                    JSON.toJSONString(param));
//            log.info("短信发送成功");
//            mailService.sendSimpleMail(user.getUsername(),"下单结果告知","下单成功,商品信息:" + JSON.toJSONString(order));
            log.info("邮箱发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Param {
        private String code;
    }
}
