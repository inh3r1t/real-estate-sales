package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusCustomerMapper;
import com.zx.business.dao.BusRealEstateMapper;
import com.zx.business.model.BusRealEstate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Resource
    private BusRealEstateMapper busRealEstateMapper;

    @Test
    public void test1() {
        BusRealEstate busRealEstate = new BusRealEstate();
//        busRealEstate.setName("testBusRealEstateName3");
//        busRealEstate.setManagerId(11131);
        busRealEstate.setId(1);
        final List<BusRealEstate> list = busRealEstateService.getList(busRealEstate);
        System.out.println(list);
    }

    @Test
    public void test2() {
        BusRealEstate busRealEstate = new BusRealEstate();
        busRealEstate.setName("testBusRealEstateName3");
        final List<BusRealEstate> list = busRealEstateService.getList(null);
        System.out.println(list);
    }

    @Test
    public void test3() {
        BusRealEstate busRealEstate = new BusRealEstate();
        busRealEstate.setOrderField("sort_weight");
        busRealEstate.setOrderType("desc");
//        final Long aLong = busRealEstateMapper.countByModel(busRealEstate);
//        System.out.println(aLong);

        final PagerModel<BusRealEstate> page = busRealEstateService.getPage(1, 100, new BusRealEstate());
        System.out.println(page);
    }

    @Test
    public void test4() {
        BusRealEstate busRealEstate = new BusRealEstate();
        busRealEstate.setName("高速东方天地qqq111");
        busRealEstate.setSortWeight(10);
        final BusRealEstate add = busRealEstateService.add(busRealEstate);
        System.out.println(add);
    }

    @Test
    public void test5() {
        BusRealEstate busRealEstate = new BusRealEstate();
        busRealEstate.setId(23);
        busRealEstate.setName("高速东方天地qq222");
        busRealEstate.setSortWeight(12);
        final int update = busRealEstateService.update(busRealEstate);
        System.out.println(update);
    }

    @Test
    public void test6() {
        BusRealEstate busRealEstate = new BusRealEstate();
        busRealEstate.setId(24);
        final List<BusRealEstate> list = busRealEstateService.getList(busRealEstate);
        System.out.println(list);
    }
}