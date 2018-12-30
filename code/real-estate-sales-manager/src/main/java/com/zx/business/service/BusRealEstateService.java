package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusRealEstateMapper;
import com.zx.business.model.BusRealEstate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/28 18:54
 */
@Service
public class BusRealEstateService {

    @Resource
    private BusRealEstateMapper busRealEstateMapper;

    public PagerModel<BusRealEstate> getPage(Integer page, Integer pageSize, BusRealEstate busRealEstate) {
        Long count = busRealEstateMapper.countByModel(busRealEstate);
        int start = (page - 1) * pageSize;
        List<BusRealEstate> busRealEstates = busRealEstateMapper.selectByPage(start, pageSize, null, null, busRealEstate);
        PagerModel<BusRealEstate> busRealEstatePage = new PagerModel<>(pageSize, page, count.intValue(), busRealEstates);
        return busRealEstatePage;
    }

    public BusRealEstate getById(Integer id) {
        return busRealEstateMapper.selectByPrimaryKey(id);
    }

    public List<BusRealEstate> getList(BusRealEstate busRealEstate) {
        List<BusRealEstate> busRealEstates = busRealEstateMapper.selectByPage(null, null, null, null, busRealEstate);
        return busRealEstates;
    }

    public int add(BusRealEstate busRealEstate) {
        return busRealEstateMapper.insertSelective(busRealEstate);
    }

    public int update(BusRealEstate busRealEstate) {
        return busRealEstateMapper.updateByPrimaryKeySelective(busRealEstate);
    }
}
