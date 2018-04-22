package com.fengwenyi.mybatis.demo1;

import com.fengwenyi.mybatis.demo1.domain.User;
import com.fengwenyi.mybatis.demo1.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Autowired
    private UserService userService;

    @Test
    public void test1 () {
        User user = userService.findById(1);
        System.out.println(user.toString());
    }

    @Test
    public void test2 () {
        User user = userService.findByNameAndAge("AAA", 20);
        System.out.println(user.toString());
        /*
        Caused by: org.apache.ibatis.binding.BindingException:
        Parameter 'name' not found. Available parameters are [arg1, arg0, param1, param2]
        */
    }

    @Test
    public void test3 () {
        User pojo = new User("AAA", 20);
        User user = userService.findByPojo(pojo);
        System.out.println(user.toString());
    }

    @Test
    public void test4 () {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "AAA");
        map.put("age", 20);
        User user = userService.findByMap(map);
        System.out.println(user.toString());
    }

    @Test
    public void test5 () {

        Object [] array = {"AAA", 20};

        List<Object> list = Arrays.asList(array);

        User user = userService.findByList(list);
        System.out.println(user.toString());
    }

    @Test
    public void test6 () {

        Object [] array = {"AAA", 20};

        User user = userService.findByArray(array);
        System.out.println(user.toString());
    }

    @Test
    public void test7 () {
        User user = userService.testGetValue1("AAA");
        System.out.println(user.toString());
    }

    @Test
    public void test8 () {
        User user = userService.testGetValue2("AAA");
        System.out.println(user.toString());
    }

    @Test
    public void test9 () {
        User user = userService.testGetValue3("user");
        System.out.println(user.toString());
    }

}
