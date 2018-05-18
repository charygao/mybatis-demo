package com.fengwenyi.demo.mybatis.user.service;

import com.fengwenyi.demo.mybatis.user.model.UserAuth;

/**
 * @author Wenyi Feng
 */
public interface IUserAuthService {

    UserAuth save (UserAuth userAuth);

    UserAuth findByAccountAndPassword (String account, String password);

    UserAuth findById (Long id);

}
