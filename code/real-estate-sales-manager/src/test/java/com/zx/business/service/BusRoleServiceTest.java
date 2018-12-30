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

    }

    @Test
    public void test2() {
        BusRole busRole = new BusRole();
        busRole.setName("testRoleName2");
        busRole.setType("2");
        int i = busRoleService.add(busRole);

        System.out.println(busRole.getId());
    }
}