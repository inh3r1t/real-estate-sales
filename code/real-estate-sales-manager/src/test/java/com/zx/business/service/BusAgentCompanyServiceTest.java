package com.zx.business.service;

import com.zx.base.common.BaseTest;
import com.zx.business.model.BusAgentCompany;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 14:43
 */
public class BusAgentCompanyServiceTest extends BaseTest {

    @Resource
    private BusAgentCompanyService busAgentCompanyService;

    @Test
    public void test1() {
        BusAgentCompany busAgentCompany = new BusAgentCompany();
        busAgentCompany.setAddress("test Address 1");
        busAgentCompany.setChargePerson("jack chen");
        busAgentCompany.setName("Miracle");
        busAgentCompany.setPhone("13242123421");
        busAgentCompanyService.addOrUpdate(busAgentCompany);
    }

    @Test
    public void test2() {
        BusAgentCompany busAgentCompany = new BusAgentCompany();
        busAgentCompany.setAddress("test Address 2");
        busAgentCompany.setId(1);
        busAgentCompanyService.addOrUpdate(busAgentCompany);
    }

    @Test
    public void test3() {
        busAgentCompanyService.delete(1);
    }
}