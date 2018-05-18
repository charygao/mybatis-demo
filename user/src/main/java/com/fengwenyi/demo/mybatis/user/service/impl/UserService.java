package com.fengwenyi.demo.mybatis.user.service.impl;

import com.fengwenyi.demo.mybatis.user.dao.UserMapper;
import com.fengwenyi.demo.mybatis.user.model.User;
import com.fengwenyi.demo.mybatis.user.model.UserExample;
import com.fengwenyi.demo.mybatis.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Wenyi Feng
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User save(User user) {

        Assert.notNull(user, "用户资料不能为空");

        Long id = user.getId();
        String name = user.getName();
        int age = user.getAge();

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andAgeEqualTo(age);

        int rsNum = -1;
        if (id != null
                && id > 0) {
            // 修改
        } else {
            //保存
            rsNum = userMapper.insertSelective(user);
        }
        if (rsNum > 0)
            return user;

        return null;
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
