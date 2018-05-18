package com.fengwenyi.demo.mybatis.user.service.impl;

import com.fengwenyi.demo.mybatis.user.dao.UserAuthMapper;
import com.fengwenyi.demo.mybatis.user.model.UserAuth;
import com.fengwenyi.demo.mybatis.user.model.UserAuthExample;
import com.fengwenyi.demo.mybatis.user.service.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author Wenyi Feng
 */
@Service
@Transactional
public class UserAuthService implements IUserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public UserAuth save(UserAuth userAuth) {

        Assert.notNull(userAuth, "用户认证信息不能为空");

        Long id = userAuth.getId();
        String account = userAuth.getAccount();
        String password = userAuth.getPassword();
        Date date = userAuth.getTimeCreate();

        UserAuthExample userAuthExample = new UserAuthExample();
        UserAuthExample.Criteria userAuthCriteria = userAuthExample.createCriteria();
        userAuthCriteria.andAccountEqualTo(account);
        userAuthCriteria.andPasswordEqualTo(password);
        userAuthCriteria.andTimeCreateEqualTo(date);

        int rsNum;
        if (id != null && id > 0)
            // 修改
            rsNum = userAuthMapper.updateByExampleSelective(userAuth, userAuthExample);
        else {
            // 添加
            rsNum = userAuthMapper.insertSelective(userAuth);
        }
        if (rsNum > 0)
            return userAuth;

        return null;
    }

    @Override
    public UserAuth findByAccountAndPassword(String account, String password) {

        UserAuthExample userAuthExample = new UserAuthExample();
        UserAuthExample.Criteria userAuthCriteria = userAuthExample.createCriteria();
        userAuthCriteria.andAccountEqualTo(account);
        userAuthCriteria.andPasswordEqualTo(password);

        List<UserAuth> userAuths = userAuthMapper.selectByExample(userAuthExample);
        if (userAuths != null
                && userAuths.size() == 1)
            return userAuths.get(0);

        return null;
    }

    @Override
    public UserAuth findById(Long id) {
        return userAuthMapper.selectByPrimaryKey(id);
    }
}
