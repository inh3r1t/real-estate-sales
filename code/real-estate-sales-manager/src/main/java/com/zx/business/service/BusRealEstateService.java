package com.zx.business.service;

import com.zx.base.model.PageCondition;
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

    public PagerModel<BusRealEstate> getList(PageCondition condition) {
        Long count = busRealEstateMapper.countByModel(null);
        List<BusRealEstate> busRealEstates = busRealEstateMapper.selectByPage(condition);
        PagerModel<BusRealEstate> busRealEstatePage = new PagerModel<>(condition.getPageSize(), condition.getPage(),count.intValue(), busRealEstates);
        return busRealEstatePage;
    }

    public BusRealEstate getById(Integer id) {
        return busRealEstateMapper.selectByPrimaryKey(id);
    }
}
