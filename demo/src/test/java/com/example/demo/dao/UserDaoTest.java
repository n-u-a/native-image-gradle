package com.example.demo.dao;

import com.example.demo.config.TestMyBatisConfig;
import com.example.demo.domain.UserDomain;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.truncate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.db.api.Assertions.assertThat;

@SpringBootTest
@Import(value = TestMyBatisConfig.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource testDataSource;

    private static final Operation TRUNCATE = truncate("user");

    private static final DbSetupTracker tracker = new DbSetupTracker();

    @BeforeEach
    public void before() {
        Operation insertUser =
                // テーブルの指定
                insertInto("user")
                        .row()
                        .column("id", "1000")
                        .column("name", "名前")
                        .column("age", "50")
                        .end()
                        // Operationオブジェクトを作成
                        .build();

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(testDataSource), Operations.sequenceOf(
                TRUNCATE, insertUser
        ));
        tracker.launchIfNecessary(dbSetup);
    }

    @Test
    void getUserTest() {
        UserDomain userDomain = userDao.getUser("1000");
        assertThat(userDomain.getAge(), is(50));
        assertThat(userDomain.getName(), is("名前"));
        assertThat(userDomain.getId(), is(1000));
        tracker.skipNextLaunch();
    }

    @Test
    void insertUserTest() {
        UserDomain userDomain = new UserDomain();
        userDomain.setAge(90);
        userDomain.setName("サンプル");
        userDomain.setId(4000);

        userDao.insertUser(userDomain);

        int id = 4000;

        Request request = new Request(testDataSource,
            "SELECT id,"
                        + "age,"
                        + "name "
                   + "FROM user "
                  + "WHERE id = " + id);

        assertThat(request)
                .column("id").value().isEqualTo(4000)
                .column("name").value().isEqualTo("サンプル")
                .column("age").value().isEqualTo(90);
    }
}
