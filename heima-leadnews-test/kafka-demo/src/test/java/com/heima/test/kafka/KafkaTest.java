package com.heima.test.kafka;


import com.heima.test.kafka.service.IHelloService;
import com.heima.test.kafka.service.impl.HelloService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class KafkaTest {

    @Autowired
    IHelloService helloService;

    @Test
    public void testKafka() {
        helloService.hello();
    }
}
