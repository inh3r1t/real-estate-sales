package com.zx.business.service;

import com.zx.base.common.BaseTest;
import com.zx.business.model.BusAgentCompany;
import org.junit.Test;

import javax.annotation.Resource;

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
//        BusAgentCompany busAgentCompany = new BusAgentCompany();
//        busAgentCompany.setAddress("合肥市百草街110号");
//        busAgentCompany.setChargePerson("张正东");
//        busAgentCompany.setName("中岳地产中介");
//        busAgentCompany.setPhone("021-88271271");
//        busAgentCompany.setState(1);
//        busAgentCompanyService.add(busAgentCompany);
        BusAgentCompany busAgentCompany = new BusAgentCompany();
        busAgentCompany.setAddress("滁州市xxx路120号");
        busAgentCompany.setChargePerson("徐正东");
        busAgentCompany.setName("阿里地产中介");
        busAgentCompany.setPhone("021-98271271");
        busAgentCompany.setState(0);
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