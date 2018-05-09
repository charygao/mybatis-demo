package com.fengwenyi.demo.mybatis.generatorconfig;

import com.fengwenyi.demo.mybatis.generatorconfig.dao.CityMapper;
import com.fengwenyi.demo.mybatis.generatorconfig.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorConfigApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private CityMapper cityMapper;

    @Test
    public void testCity () {
        List<City> cities = cityMapper.selectByExample(null);
        for (City city : cities)
            System.out.println(city);
    }

}
