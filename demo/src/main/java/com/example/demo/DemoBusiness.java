package com.example.demo;

import com.example.demo.dao.SampleDao;
import com.example.demo.domain.UserDomain;
import com.example.demo.model.Person;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.SerializationHint;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@NativeHint
@SerializationHint(types = {Person.class})
@Component
public class DemoBusiness implements Function<Message<Person>, Message<Person>> {

    @Autowired
    private SampleDao sampleDao;

    private static final Logger logger = LoggerFactory.getLogger(DemoBusiness.class);

    @Override
    public Message<Person> apply(Message<Person> request) {
        Person person = request.getPayload();
        Map<String, Object> resultHeader = new HashMap();
        resultHeader.put("statusCode", HttpStatus.CREATED.value());

        UserDomain userDomain = sampleDao.getUser(person.getId());

        logger.info(System.getenv("DEFAULT_HANDLER"));
        logger.info("ログ確認");

        Person response = new Person();
        response.setName(userDomain.getName());
        response.setId(person.getId());
        response.setAge("9696");

        return new GenericMessage(response, resultHeader);
    }
}