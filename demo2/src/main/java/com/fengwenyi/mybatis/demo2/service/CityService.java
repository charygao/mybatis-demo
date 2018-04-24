package com.fengwenyi.mybatis.demo2.service;

import com.fengwenyi.mybatis.demo2.dao.CityDao;
import com.fengwenyi.mybatis.demo2.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Wenyi Feng
 */
@Service
public class CityService {

    @Autowired
    private CityDao cityDao;

    public City findById (Integer id) {
        if (id != null && id > 0) {
            return cityDao.findById(id);
        }
        return null;
    }

    public City findById2 (Integer id) {
        if (id != null && id > 0) {
            return cityDao.findById2(id);
        }
        return null;
    }

    public Map<Object, Object> findByIdReturnMap (Integer id) {
        if (id != null && id > 0) {
            return cityDao.findByIdReturnMap(id);
        }
        return null;
    }

    public Map<Object, City> findReturnMap () {
        return cityDao.findReturnMap();
    }

    public City findById3 (Integer id) {
        if (id != null && id > 0) {
            return cityDao.findById3(id);
        }
        return null;
    }

    public City findById4 (Integer id) {
        if (id != null && id > 0) {
            return cityDao.findById4(id);
        }
        return null;
    }

    public City findById5 (Integer id) {
        if (id != null && id > 0) {
            return cityDao.findById5(id);
        }
        return null;
    }
}
