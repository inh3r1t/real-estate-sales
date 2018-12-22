package com.zx.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
    public void getList() {
//        final PagerModel<BusRealEstate> list = busRealEstateService.getList();
//        System.out.println(list);
    }
}