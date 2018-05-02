package com.fengwenyi.demo.mybatis.cache;

import com.fengwenyi.demo.mybatis.cache.dao.IUserDao;
import com.fengwenyi.demo.mybatis.cache.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private IUserDao userDao;

    @Test
    public void testFirstLevelCache() {
        User user1 = userDao.findById(1);
        System.out.println(user1.toString());

        User user2 = userDao.findById(1);
        System.out.println(user2.toString());
    }

}
