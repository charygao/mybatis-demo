package com.fengwenyi.mybatis.demo2.service;

import com.fengwenyi.mybatis.demo2.dao.ProvinceDao;
import com.fengwenyi.mybatis.demo2.domain.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wenyi Feng
 */
@Service
public class ProvinceService {

    @Autowired
    private ProvinceDao provinceDao;

    public Province findById (Integer id) {
        if (id != null && id > 0) {
            return provinceDao.findById(id);
        }
        return null;
    }

    public Province findById3 (Integer id) {
        if (id != null && id > 0) {
            return provinceDao.findById3(id);
        }
        return null;
    }

}
