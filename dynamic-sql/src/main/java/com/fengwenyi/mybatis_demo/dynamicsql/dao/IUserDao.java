package com.fengwenyi.mybatis_demo.dynamicsql.dao;

import com.fengwenyi.mybatis_demo.dynamicsql.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
