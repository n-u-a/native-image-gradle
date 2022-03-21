package com.example.demo;

import com.example.demo.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class DemoBusinessTest {

    @Autowired
    private DemoBusiness business;

    @Test
    void sampleTest() {

        Person payload = new Person();
        payload.setName("test");
        payload.setAge("20");
        payload.setId("1111");

        Message<Person> param = MessageBuilder.withPayload(payload).build();

        Message<Person> response = business.apply(param);
        assertEquals(response.getPayload().getName(), "test");
        assertEquals(response.getPayload().getAge(), "20");
        assertEquals(response.getPayload().getId(), "1111");
    }
}
