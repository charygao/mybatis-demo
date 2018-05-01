package com.fengwenyi.mybatis_demo.dynamicsql.dao;

import com.fengwenyi.mybatis_demo.dynamicsql.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wenyi Feng
 */
@Mapper
public interface IUserDao {

    // test if
    User testIf (@Param("id") Integer id);

    // test where
    User testWhere (@Param("id") Integer id);

    // test trim
    User testTrim (@Param("id") Integer id);

    // test choose
    User testChoose (@Param("id") Integer id);

    // test foreach
    User testForeach (@Param("ids") Integer ... ids);

    // test set
    int testUpdate (@Param("user") User user);

    // test batch inset
    int testBatchInsert1 (@Param("users") List<User> users);

    // 返回的影响行数不正确
    // int testBatchInsert2 (@Param("users") List<User> users);
    // mysql url:spring.datasource.url=jdbc:mysql://localhost:3306/mybatis_demo?allowMultiQueries=true

    // test _parameter
    List<User> test_parameter1 (String name);
    List<User> test_parameter2 (User user);

    // test bind
    List<User> testBind (String name);
    List<User> testLike (String name);

}
