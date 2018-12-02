package com.zx.business.service;

import com.zx.base.model.PageCondition;
import com.zx.base.model.PagerModel;
import com.zx.business.model.BusRealEstate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

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
        PageCondition pageCondition = new PageCondition(10, 1, "id", "DESC");
        final PagerModel<BusRealEstate> list = busRealEstateService.getList(pageCondition);
        System.out.println(list);
    }
}