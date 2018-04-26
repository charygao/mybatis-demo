package com.fengwenyi.mybatis_demo.dynamicsql;

import com.fengwenyi.mybatis_demo.dynamicsql.dao.IUserDao;
import com.fengwenyi.mybatis_demo.dynamicsql.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicSqlApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private IUserDao userDao;

    @Test
    public void testIf () {
        User user = userDao.testIf(3);
        System.out.println(user.toString());
        user = userDao.testIf(1);
        // SQL: SELECT           id, name, age         FROM           user         WHERE
        System.out.println(user.toString());
    }

    @Test
    public void testWhere () {
        User user = userDao.testWhere(3);
        System.out.println(user.toString());
        user = userDao.testWhere(1);
        // ==>  Preparing: SELECT id, name, age FROM user
        System.out.println(user.toString());
    }

    @Test
    public void testTrim () {
        User user = userDao.testTrim(3);
        // ==>  Preparing: SELECT id, name, age FROM user where id = ?
        System.out.println(user.toString());
        user = userDao.testTrim(1);
        // ==>  Preparing: SELECT id, name, age FROM user
        System.out.println(user.toString());
    }

    @Test
    public void testChoose () {
        User user = userDao.testChoose(4);
        // ==>  Preparing: SELECT id, name, age FROM user WHERE id = '3'
        System.out.println(user.toString());
        user = userDao.testChoose(1);
        // ==>  Preparing: SELECT id, name, age FROM user WHERE id = ?
        System.out.println(user.toString());
    }

    @Test
    public void testForeach () {
        User user = userDao.testForeach(5);
        // ==>  SELECT id, name, age FROM user WHERE id IN ( ? )
        System.out.println(user.toString());

        user = userDao.testForeach(1, 5, 3);
        // ==>  SELECT id, name, age FROM user WHERE id IN ( ? , ? , ? )
        System.out.println(user.toString());
    }

}
