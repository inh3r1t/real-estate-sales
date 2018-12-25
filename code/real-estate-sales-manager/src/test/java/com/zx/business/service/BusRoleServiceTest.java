package com.zx.business.service;

import com.zx.base.common.BaseTest;
import com.zx.business.model.BusRole;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/25 20:31
 */
public class BusRoleServiceTest extends BaseTest {

    @Resource
    private BusRoleService busRoleService;

    @Test
    public void test1() {
//        BusRole busRole = new BusRole();
//        busRole.setName("testRoleName1");
//        busRole.setType("1");
//        int id = busRoleService.add(busRole);
//
//        System.out.println(id);

        BusRole condition = new BusRole();
        condition.setName("testRoleName1");
        final BusRole busRole1 = busRoleService.getBusRole(condition);
        System.out.println(busRole1);


    }
}