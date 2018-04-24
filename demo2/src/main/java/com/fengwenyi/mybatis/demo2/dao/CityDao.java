package com.fengwenyi.mybatis.demo2.dao;

import com.fengwenyi.mybatis.demo2.domain.City;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Wenyi Feng
 */
@Mapper // 可以在Application中通过@MapperScan("com.fengwenyi.mybatis.demo2.dao")来配置
public interface CityDao {

    City findById (Integer id);

    City findById2 (Integer id);

    Map<Object, Object> findByIdReturnMap (Integer id);

    //@MapKey("id")
    @MapKey("name")
    Map<Object, City> findReturnMap ();

    City findById3 (Integer id);

    City findById4 (Integer id);


    City findById5 (Integer id);

    City findByProvinceId (Integer pId);

    List<City> findAll ();
}
