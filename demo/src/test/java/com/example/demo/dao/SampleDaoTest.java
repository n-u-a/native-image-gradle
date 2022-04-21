package com.example.demo.dao;

import com.example.demo.domain.UserDomain;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.truncate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class SampleDaoTest extends DaoTestBase {

    @Autowired
    private SampleDao sampleDao;

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

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource()), Operations.sequenceOf(
                TRUNCATE, insertUser
        ));
        tracker.launchIfNecessary(dbSetup);
    }

    @Test
    void getUserTest() {
        UserDomain userDomain = sampleDao.getUser("1000");
        assertThat(userDomain.getAge(), is(50));
        tracker.skipNextLaunch();
    }

}
