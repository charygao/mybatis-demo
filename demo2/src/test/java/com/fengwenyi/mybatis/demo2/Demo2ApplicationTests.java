package com.fengwenyi.mybatis.demo2;

import com.fengwenyi.mybatis.demo2.domain.City;
import com.fengwenyi.mybatis.demo2.domain.Province;
import com.fengwenyi.mybatis.demo2.service.CityService;
import com.fengwenyi.mybatis.demo2.service.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo2ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private CityService cityService;

    @Test
    public void test1 () {

        City city = cityService.findById(1);

        System.out.println(city.toString());

    }

    @Test
    public void test2 () {

        City city = cityService.findById2(1);

        System.out.println(city.toString());

    }

    @Test
    public void test3 () {

        Map<Object, Object> map = cityService.findByIdReturnMap(1);

        System.out.println(map);

    }

    @Test
    public void test4 () {

        Map<Object, City> map = cityService.findReturnMap();

        System.out.println(map);

    }

    @Test
    public void test5 () {

        City city = cityService.findById3(1);

        System.out.println(city.toString());
        System.out.println(city.getProvince().toString());

    }

    @Autowired
    private ProvinceService provinceService;

    @Test
    public void test6 () {
        Province province = provinceService.findById(1);
        System.out.println(province.toString());

        List<City> cities = province.getCities();
        for (City city : cities) {
            System.out.println(city.toString());
        }
    }

    @Test
    public void test7 () {
        City city = cityService.findById4(1);
        System.out.println(city.toString());
        System.out.println(city.getProvince().toString());
    }

    @Test
    public void test8 () {
        Province province = provinceService.findById3(1);
        System.out.println(province.toString());

        List<City> cities = province.getCities();
        for (City city : cities) {
            System.out.println(city.toString());
        }
    }

    @Test
    public void test9 () {
        City city = cityService.findById5(1);
        System.out.println(city.toString());
    }

}
