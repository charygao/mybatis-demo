package com.fengwenyi.mybatis.demo2.dao;

import com.fengwenyi.mybatis.demo2.domain.Province;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Wenyi Feng
 */
@Mapper
public interface ProvinceDao {

    Province findById (Integer id);

    Province findById2 (Integer id);

    Province findById3 (Integer id);

}
