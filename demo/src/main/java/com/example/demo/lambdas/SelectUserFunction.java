package com.example.demo.lambdas;

import com.example.demo.dao.SampleDao;
import com.example.demo.domain.UserDomain;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.SerializationHint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@NativeHint
@SerializationHint(types = {Person.class})
@Component
public class SelectUserFunction implements Function<Message<Person>, Message<Person>> {

    @Autowired
    private SampleDao sampleDao;

    @Override
    public Message<Person> apply(Message<Person> request) {
        Person person = request.getPayload();
        Map<String, Object> resultHeader = new HashMap();
        resultHeader.put("statusCode", HttpStatus.OK.value());

        UserDomain userDomain = sampleDao.getUser(person.getId());

        Person response = new Person();
        response.setName(userDomain.getName());
        response.setId(String.valueOf(userDomain.getId()));
        response.setAge(String.valueOf(userDomain.getAge()));

        return new GenericMessage(response, resultHeader);
    }
}
