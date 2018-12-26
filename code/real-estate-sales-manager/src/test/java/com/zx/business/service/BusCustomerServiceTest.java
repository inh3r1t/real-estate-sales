package com.zx.business.service;

import com.zx.base.common.BaseTest;
import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusCustomerMapper;
import com.zx.business.model.BusCustomer;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/25 21:38
 */
public class BusCustomerServiceTest extends BaseTest {

    @Resource
    private BusCustomerService busCustomerService;

    @Resource
    private BusCustomerMapper busCustomerMapper;

    @Test
    public void test1() {
        BusCustomer busCustomer = new BusCustomer();
        busCustomer.setName("testName1");
        busCustomer.setAgentId(111);
        busCustomer.setSex(0);
//        final List<BusCustomer> list = busCustomerService.getList(busCustomer);
//        System.out.println(list);

//        final Long aLong = busCustomerMapper.countByModel(busCustomer);
//        System.out.println(aLong);

        final PagerModel<BusCustomer> page = busCustomerService.getPage(1, 3, busCustomer);
        System.out.println(page);

    }

    @Test
    public void test2() {
        BusCustomer busCustomer = new BusCustomer();
        busCustomer.setName("testName1");
        busCustomer.setAgentId(111);
        busCustomer.setSex(0);
        final int i = busCustomerMapper.insertSelective(busCustomer);
        System.out.println(i);
    }
}