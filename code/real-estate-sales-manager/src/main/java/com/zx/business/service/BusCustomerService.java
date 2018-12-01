package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusCustomerMapper;
import com.zx.business.model.BusCustomer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/29 23:44
 */
@Service
public class BusCustomerService {

    @Resource
    private BusCustomerMapper busCustomerMapper;

    public PagerModel<BusCustomer> getPage(Integer page, Integer pageSize, BusCustomer busCustomer) {
        Long count = busCustomerMapper.countByModel(busCustomer);
        int start = (page + 1) * pageSize;
        List<BusCustomer> busCustomers = busCustomerMapper.selectByPage(start, pageSize, null, null, busCustomer);
        return new PagerModel<BusCustomer>(page, pageSize, count.intValue(), busCustomers);
    }

    public List<BusCustomer> getList(BusCustomer busCustomer) {
        List<BusCustomer> busCustomers = busCustomerMapper.selectByPage(null, null, null, null, busCustomer);
        return busCustomers;
    }
}
