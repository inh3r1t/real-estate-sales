package com.zx.business.service;

import com.zx.base.common.BaseTest;
import com.zx.business.dao.BusDealMapper;
import com.zx.business.model.BusDeal;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/25 20:56
 */
public class BusDealServiceTest extends BaseTest {

    @Resource
    private BusDealService busDealService;

    @Resource
    private BusDealMapper busDealMapper;

    @Test
    public void test1() {
        BusDeal busDeal = new BusDeal();
        busDeal.setRealEstateId(2);
        busDeal.setSubscribeMoney("100");
        busDeal.setSubscribeTime(new Date());
        busDeal.setSubscribeOperateTime(new Date());
        busDeal.setArriveOperateTime(new Date());
        busDeal.setAppointmentOperateTime(new Date());
        busDeal.setCustomerId(3);
        busDeal.setCustomerPhone("132423421223");
        busDeal.setCustomerName("Tony");
        busDeal.setRealEstateName("碧春园");
        busDeal.setReportCompany("testCompany");
        final int id = busDealMapper.insertSelective(busDeal);
    }

    @Test
    public void test2() {
        BusDeal busDeal = new BusDeal();
        busDeal.setRealEstateId(2);
        busDeal.setSubscribeMoney("100");
        busDeal.setCustomerId(3);
        busDeal.setCustomerPhone("132423421223");
        busDeal.setCustomerName("Tony");
        busDeal.setRealEstateName("碧春园");
        busDeal.setReportCompany("testCompany");

        final List<BusDeal> busDeals = busDealMapper.selectByPage(null, null, null, null, busDeal);
        System.out.println(busDeals);
    }
}