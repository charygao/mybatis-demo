package com.fengwenyi.demo.mybatis.test;

import com.fengwenyi.demo.mybatis.dao.UserMapper;
import com.fengwenyi.demo.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 测试类
 * @author Wenyi Feng
 */
public class MyBatisTest {

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        // mybatis配置文件
        String resource = "mybatis-config.xml";
        // 得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 创建会话工厂，传入mybatis的配置文件信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    // 根据id查询用户信息，得到一条记录结果
    @Test
    public void test () {
        SqlSession sqlSession = null;
        try {
            sqlSession = this.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            System.out.println(userMapper);
            // org.apache.ibatis.binding.MapperProxy@6325a3ee
            User user = userMapper.findById(1);
            System.out.println(user.toString());
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (sqlSession != null) sqlSession.close();
        }
    }
}
