package com.fengwenyi.demo.mybatis.dao;

import com.fengwenyi.demo.mybatis.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper 接口
 * @author Wenyi Feng
 */

public interface UserMapper {

    User findById (@Param("id") Integer id);

}
