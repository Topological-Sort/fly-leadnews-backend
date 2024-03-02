package com.heima.test.kafka.service.impl;


import com.alibaba.fastjson.JSON;
import com.heima.test.kafka.pojos.User;
import com.heima.test.kafka.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HelloService implements IHelloService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void hello(){
        User user = new User();
        user.setUsername("xiaowang");
        user.setAge(18);
        kafkaTemplate.send("user-topic", JSON.toJSONString(user));
    }
}
