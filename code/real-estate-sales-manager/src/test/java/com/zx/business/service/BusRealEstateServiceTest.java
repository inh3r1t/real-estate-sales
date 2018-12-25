package com.zx.business.service;

import com.zx.business.model.BusRealEstate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/28 19:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BusRealEstateServiceTest {

    @Resource
    private BusRealEstateService busRealEstateService;

    @Test
    public void test1() {
        BusRealEstate busRealEstate = new BusRealEstate();
        busRealEstate.setName("testBusRealEstateName3");
        busRealEstate.setManagerId(11131);
        int id = busRealEstateService.add(busRealEstate);
        System.out.println(id);
    }

    @Test
    public void test2() {
        BusRealEstate busRealEstate = new BusRealEstate();
        busRealEstate.setName("testBusRealEstateName3");
        final List<BusRealEstate> list = busRealEstateService.getList(busRealEstate);
        System.out.println(list);
    }
}