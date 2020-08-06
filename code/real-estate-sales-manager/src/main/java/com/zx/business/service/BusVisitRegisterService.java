package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusCustomerMapper;
import com.zx.business.dao.BusRealEstateMapper;
import com.zx.business.dao.BusVisitRegisterMapper;
import com.zx.business.model.BusCustomer;
import com.zx.business.model.BusRealEstate;
import com.zx.business.model.BusVisitRegister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/29 23:44
 */
@Service
public class BusVisitRegisterService {

    @Resource
    private BusVisitRegisterMapper busVisitRegisterMapper;


    public PagerModel<BusVisitRegister> getPage(Integer page, Integer pageSize, String startDateTime, String endDateTime) {
        Integer count = busVisitRegisterMapper.countByModel(startDateTime, endDateTime);
        int start = (page - 1) * pageSize;
        List<BusVisitRegister> busVisitRegisters = busVisitRegisterMapper.selectByPage(start, pageSize, null, null,startDateTime, endDateTime);
        return new PagerModel<>(pageSize, page, count.intValue(), busVisitRegisters);
    }

    public List<BusVisitRegister> getList( String startDateTime, String endDateTime) {
        return busVisitRegisterMapper.selectByPage(null, null, null, null, startDateTime, endDateTime);
    }
}
