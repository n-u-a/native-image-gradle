package com.example.demo.lambdas;

import com.example.demo.dao.SampleDao;
import com.example.demo.domain.UserDomain;
import com.example.demo.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SelectUserFunctionTest {

    @InjectMocks
    @Autowired
    private SelectUserFunction business;

    @Mock
    private SampleDao sampleDao;

    @Test
    void sampleTest() {

        // Daoのレスポンス
        UserDomain response = new UserDomain();
        response.setName("サンプル");
        response.setId(1);
        response.setAge(99);

        // Daoのメソッドをモック
        when(sampleDao.getUser(any())).thenReturn(response);

        // パラメータを作成してメソッドを実行
        Message<Person> param = MessageBuilder.withPayload(new Person()).build();
        Message<Person> actual = business.apply(param);

        // 結果を比較
        assertThat(actual.getPayload().getName(), is("サンプル"));
        assertThat(actual.getPayload().getAge(), is("99"));
        assertThat(actual.getPayload().getId(), is("1"));
    }
}
