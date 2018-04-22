package com.fengwenyi.mybatis.demo1.dao;

import com.fengwenyi.mybatis.demo1.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Wenyi Feng
 */
@Mapper
public interface UserDao {

    // 查询
    User findById (Integer id);

    User findByNameAndAge (String name, Integer age);

    User findByNameAndAge2 (@Param("name") String name, @Param("age") Integer age);

    User findByPojo (User user);

    User findByMap (Map<String, Object> map);

    User findByList (List<Object> list);

    User findByArray (Object [] array);

    User testGetValue1 (String name);

    User testGetValue2 (@Param("name") String name);

    User testGetValue3 (@Param("name") String name);

}
