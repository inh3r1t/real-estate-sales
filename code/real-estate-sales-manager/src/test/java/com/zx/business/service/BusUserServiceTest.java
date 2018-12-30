package com.zx.business.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zx.base.common.BaseTest;
import com.zx.business.dao.BusUserMapper;
import com.zx.business.model.BusUser;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class BusUserServiceTest extends BaseTest {

    @Resource
    private BusUserService busUserService;

    @Resource
    private BusUserMapper busUserMapper;

    @Test
    public void test1() {
        String sign = JWT.create().withAudience("testName").sign(Algorithm.HMAC256("testPasswd"));
        System.out.println(sign);

        String testName = JWT.decode(sign).getAudience().get(0);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("testPasswd1")).build();
        DecodedJWT verify = jwtVerifier.verify(sign);
        System.out.println(verify);
    }

    @Test
    public void test2() {

        BusUser busUser = new BusUser();
        busUser.setUserName("testUserName1");
        busUser.setPasswd("123456");
        busUser.setCompanyName("testCompanyName1");
        busUser.setOpenId("testOpenId1");

        final int id = busUserService.addBusUser(busUser);

        System.out.println(id);

        BusUser condition = new BusUser();
        condition.setUserName("testUserName1");
        BusUser busUser1 = busUserService.getBusUser(condition);
        System.out.println(busUser1);

    }

    @Test
    public void test3() {


        BusUser busUser = new BusUser();
        busUser.setUserName("testUserName1");

//        final long l = busUserMapper.countByModel(busUser);
//        System.out.println(l);

        final List<BusUser> busUsers = busUserMapper.selectByPage(0, 4, null, null, busUser);
        System.out.println(busUsers);
    }

    @Test
    public void test4() {

        BusUser busUser = new BusUser();
        busUser.setId(6);
        busUser = busUserService.getBusUser(busUser);
        String token = JWT.create().withAudience(String.valueOf(busUser.getId()))
                .sign(Algorithm.HMAC256(busUser.getPasswd()));
        System.out.println(token);
        System.out.println("---------------------------------");
    }

    @Test
    public void test5() {
        final List<BusUser> busUsers = busUserMapper.selectByModel(new BusUser());
        System.out.println(busUsers);
    }

    @Test
    public void test6() {
        final List<BusUser> listByRoleType = busUserService.getListByRoleType(0);
        System.out.println(listByRoleType);
    }
}