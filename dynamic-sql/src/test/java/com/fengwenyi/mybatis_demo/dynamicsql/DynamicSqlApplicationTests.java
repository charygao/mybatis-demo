package com.fengwenyi.mybatis_demo.dynamicsql;

import com.fengwenyi.mybatis_demo.dynamicsql.dao.IUserDao;
import com.fengwenyi.mybatis_demo.dynamicsql.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testUpdate () {
        User user = userDao.testIf(3);

        user.setName("Mr.zhang");
        int rsNum = userDao.testUpdate(user);
        System.out.println(rsNum);

        user.setName("");
        rsNum = userDao.testUpdate(user);
        System.out.println(rsNum);

        user.setAge(-1);
        rsNum = userDao.testUpdate(user);
        System.out.println(rsNum);

        user.setAge(30);
        rsNum = userDao.testUpdate(user);
        System.out.println(rsNum);
    }

    @Test
    public void testBatchInsert1 () {
        List<User> users = new ArrayList<>();
        User u1 = new User("n1", 11);
        users.add(u1);
        User u2 = new User("n2", 12);
        users.add(u2);
        int rsNum = userDao.testBatchInsert1(users);
        System.out.println("rs num : " + rsNum);
    }

    /*@Test
    public void testBatchInsert2 () {
        List<User> users = new ArrayList<>();
        User u1 = new User("n3", 11);
        users.add(u1);
        User u2 = new User("n4", 12);
        users.add(u2);
        int rsNum = userDao.testBatchInsert2(users);
        System.out.println("rs num : " + rsNum);
    }*/

    @Test
    public void test_parameter1 () {
        List<User> users = userDao.test_parameter1("n1");

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void test_parameter2 () {
        User u = new User();
        u.setName("AAA");
        u.setAge(20);
        List<User> users = userDao.test_parameter2(u);

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void testBind () {
        List<User> users = userDao.testBind("n");

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void testLike () {
        List<User> users = userDao.testLike("%n%");

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

}
