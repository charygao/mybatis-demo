package com.fengwenyi.demo.mybatis.cache.dao;

import com.fengwenyi.demo.mybatis.cache.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author Wenyi Feng
 */
@Mapper
public interface IUserDao {

    User findById(@Param("id") Integer id);

}
