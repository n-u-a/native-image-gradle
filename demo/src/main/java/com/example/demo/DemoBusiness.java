package com.example.demo;

import com.example.demo.model.Person;
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
public class DemoBusiness implements Function<Message<Person>, Message<Person>> {

    @Override
    public Message<Person> apply(Message<Person> request) {
        Person person = request.getPayload();
        Map<String, Object> resultHeader = new HashMap();
        resultHeader.put("statusCode", HttpStatus.CREATED.value());
        resultHeader.put("X-Custom-Header", "Hello World from Spring Cloud Function AWS Adapter");
        return new GenericMessage(person, resultHeader);
    }
}