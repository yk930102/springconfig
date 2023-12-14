package com.ali.test;

import com.ali.OrderApplication;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//测试
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class MessageTypeTest {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    //同步消息
    @Test
    public void testSyncSend() {
        //参数一: topic， 如果想添加tag 可以使用"topic:tag"的写法
        //参数二: 消息内容
        SendResult sendResult =
                rocketMQTemplate.syncSend("test-topic-1", "这是一条同步消息");
        System.out.println(sendResult);
    }

    //异步消息
    @Test
    public void testAsyncSend() throws InterruptedException {
        //参数一: topic, 如果想添加tag 可以使用"topic:tag"的写法
        //参数二: 消息内容
        //参数三: 回调函数, 处理返回结果
        rocketMQTemplate.asyncSend("test-topic-1", "这是一条异步消息", new
                SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println(sendResult);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        System.out.println(throwable);
                    }
                });
        //让线程不要终止
        Thread.sleep(30000000);
    }

    //单向消息
    @Test
    public void testOneWay() {
        rocketMQTemplate.sendOneWay("test-topic-1", "这是一条单向消息");
    }
}
