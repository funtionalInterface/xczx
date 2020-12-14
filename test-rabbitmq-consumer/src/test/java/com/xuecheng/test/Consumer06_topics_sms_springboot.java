package com.xuecheng.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Consumer06_topics_sms_springboot {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void receive_sms() {

    }
}
