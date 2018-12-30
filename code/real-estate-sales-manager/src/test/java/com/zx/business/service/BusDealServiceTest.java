package com.zx.business.service;

import com.zx.base.common.BaseTest;
import com.zx.business.dao.BusDealMapper;
import com.zx.business.model.BusDeal;
import com.zx.business.vo.BusDealVO;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Test
    public void test3() {
        BusDeal busDeal = new BusDeal();
        busDeal.setRealEstateName("碧春园");

//        final Long aLong = busDealMapper.countByModel(busDeal);
//        System.out.println(aLong);

        final List<BusDeal> busDeals = busDealMapper.selectByPage(1, 4,null,null, busDeal);
        System.out.println(busDeals);

    }

    @Test
    public void test4() {
        BusDealVO busDealVO = new BusDealVO();
        busDealVO.setCustomerName("tony");
        busDealVO.setCustomerPhone("123421231234");
        busDealVO.setCustomerSex(1);
        busDealVO.setRealEstateIds("1,2");
        busDealVO.setReportUserId(4);
        busDealVO.setReportTime(new Date());
        busDealService.report(busDealVO);
    }

    @Test
    public void test5() {

        BusDeal busDeal = new BusDeal();
        busDeal.setId(19);
        busDeal.setAppointmentTime(new Date());
        busDealService.appointment(busDeal);
    }

    @Test
    public void test6() {
        BusDeal busDeal = new BusDeal();
        busDeal.setId(19);
        busDeal.setArriveTime(new Date());
        busDealService.arrive(busDeal);
    }

    @Test
    public void test7() {
        BusDeal busDeal = new BusDeal();
        busDeal.setId(19);
        busDeal.setSubscribeTime(new Date());
        busDealService.subscribe(busDeal);
    }

    @Test
    public void test8() {
        final Map<String, Long> stringLongMap = busDealService.countByState(3);
        System.out.println(stringLongMap);
    }
}