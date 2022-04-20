package com.example.demo;

import com.example.demo.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class DemoBusinessTest {

    @Autowired
    private DemoBusiness business;

    @Test
    void sampleTest() {

        Person payload = new Person();
        payload.setName("a");
        payload.setAge("20");
        payload.setId("1");

        Message<Person> param = MessageBuilder.withPayload(payload).build();

        Message<Person> response = business.apply(param);
        assertThat(response.getPayload().getName(), is("サンプル"));
        assertThat(response.getPayload().getAge(), is("9696"));
        assertThat(response.getPayload().getId(), is("1"));
    }
}
