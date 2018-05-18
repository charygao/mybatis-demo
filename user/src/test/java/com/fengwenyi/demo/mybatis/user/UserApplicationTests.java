package com.fengwenyi.demo.mybatis.user;

import com.fengwenyi.demo.mybatis.user.bean.UserBean;
import com.fengwenyi.demo.mybatis.user.dao.UserAuthMapper;
import com.fengwenyi.demo.mybatis.user.model.User;
import com.fengwenyi.demo.mybatis.user.model.UserAuth;
import com.fengwenyi.demo.mybatis.user.model.UserAuthExample;
import com.fengwenyi.demo.mybatis.user.service.IUserAuthService;
import com.fengwenyi.demo.mybatis.user.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserAuthService userAuthService;

    @Test
    public void register () {

        Date date = new Date();

        String name = "Wenyi Feng";
        int age = 22;
        String account = "888";
        String password = "sdgt@#$%fjtyu6hwer4";

        UserAuth userAuth = new UserAuth();
        userAuth.setAccount(account);
        userAuth.setPassword(password);
        userAuth.setTimeCreate(date);

        User user = new User();
        user.setName(name);
        user.setAge(age);

        userAuth = userAuthService.save(userAuth);

        if (userAuth != null) {
            user = userService.save(user);
            if (user != null) {
                Long userId = user.getId();
                userAuth.setUserId(userId);
                userAuth = userAuthService.save(userAuth);
                if (userAuth != null) {
                    System.out.println("注册成功");
                } else {
                    System.out.println("user auth 更新出错");
                }
            } else {
                System.out.println("userId 出错");
            }
        } else {
            System.out.println("user auth 保存出错");
        }
    }

    // 登录
    @Test
    public void login () {
        String account = "222";
        String password = "xfsy2018";

        UserAuth userAuth = userAuthService.findByAccountAndPassword(account, password);
        if (userAuth != null) {
            Long userId = userAuth.getUserId();
            if (userId != null
                    && userId > 0) {
                User user = userService.findById(userId);
                if (user != null) {
                    System.out.println("登录成功");

                    UserBean userBean = new UserBean(user.getId(),
                            userAuth.getId(),
                            user.getName(),
                            user.getAge(),
                            userAuth.getAccount(),
                            userAuth.getTimeCreate(),
                            new Date());
                    System.out.println("用户信息：");

                    System.out.println(userBean.toString());

                } else {
                    System.out.println("登录失败，用户资料查询失败");
                }
            } else {
                System.out.println("登录失败，用户账户异常");
            }
        } else {
            System.out.println("登录失败，账号或密码不正确");
        }
    }

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Test
    public void test () {
        String account = "222";
        String password = "876";

        UserAuth userAuth = new UserAuth();
        userAuth.setAccount(account);
        userAuth.setPassword(password);
        userAuth.setTimeCreate(new Date());
        int rsNum = userAuthMapper.insertSelective(userAuth);


        /*userAuth.setUserId(16L);
        UserAuthExample userAuthExample = new UserAuthExample();
        UserAuthExample.Criteria userAuthCriteria = userAuthExample.createCriteria();
        userAuthCriteria.andAccountEqualTo(account);
        userAuthCriteria.andPasswordEqualTo(password);
        int rsNum = userAuthMapper.updateByExampleSelective(userAuth, userAuthExample);*/
        System.out.println(rsNum);

        System.out.println("-----------------");

        System.out.println(userAuth.getId());
    }

    @Test
    public void test3 () {

    }

}
