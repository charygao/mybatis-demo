package com.fengwenyi.demo.mybatis.user;

import com.fengwenyi.demo.mybatis.user.dao.UserAuthMapper;
import com.fengwenyi.demo.mybatis.user.dao.UserMapper;
import com.fengwenyi.demo.mybatis.user.model.User;
import com.fengwenyi.demo.mybatis.user.model.UserAuth;
import com.fengwenyi.demo.mybatis.user.model.UserAuthExample;
import com.fengwenyi.demo.mybatis.user.model.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    public void register () {

        Date date = new Date();

        String name = "小冯少爷";
        int age = 20;
        String account = "13550495918";
        String password = "xfsy2018";

        UserAuth userAuth = new UserAuth();
        userAuth.setAccount(account);
        userAuth.setPassword(password);
        userAuth.setTimeCreate(date);

        int rs1 = userAuthMapper.insertSelective(userAuth);
        if (rs1 > 0) {
            User user = new User();
            user.setName(name);
            user.setAge(age);
            int rs2 = userMapper.insert(user);
            if (rs2 > 0) {

                UserExample userExample = new UserExample();
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andNameEqualTo(name);
                criteria.andAgeEqualTo(20);

                List<User> users = userMapper.selectByExample(userExample);
                if (users != null) {

                    if (users.size() == 1) {
                        user = users.get(0);
                        Long id = user.getId();
                        userAuth.setUserId(id);
                        UserAuthExample userAuthExample = new UserAuthExample();
                        UserAuthExample.Criteria userAuthCriteria = userAuthExample.createCriteria();
                        userAuthCriteria.andAccountEqualTo(account);
                        userAuthCriteria.andPasswordEqualTo(password);
                        userAuthCriteria.andTimeCreateEqualTo(date);
                        int rs3 = userAuthMapper.updateByExampleSelective(userAuth, userAuthExample);
                        if (rs3 > 0) {
                            System.out.println("注册成功");
                        } else {
                            System.out.println("注册失败");
                        }
                    } else {
                        System.out.println(users.size());
                    }

                } else {
                    System.out.println("users null");
                }

            } else {
                System.out.println("用户信息保存失败");

                // 删除注册信息

            }
        } else {
            System.out.println("账号保存失败");
        }
    }

}
