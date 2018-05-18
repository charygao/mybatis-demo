package com.fengwenyi.demo.mybatis.user.service;

import com.fengwenyi.demo.mybatis.user.model.User;

/**
 * @author Wenyi Feng
 */
public interface IUserService {

    User save (User user);

    User findById (Long id);

}
