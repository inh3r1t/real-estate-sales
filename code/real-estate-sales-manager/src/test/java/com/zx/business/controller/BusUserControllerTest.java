package com.zx.business.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

public class BusUserControllerTest {

    @Test
    public void test1() {
        String sign = JWT.create().withAudience("testName").sign(Algorithm.HMAC256("testPasswd"));
        System.out.println(sign);

        String testName = JWT.decode(sign).getAudience().get(0);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("testPasswd1")).build();
        DecodedJWT verify = jwtVerifier.verify(sign);
        System.out.println(verify);
    }
}