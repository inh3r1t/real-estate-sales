package com.zx.business.service;

import com.zx.lib.http.entity.HttpEntity;
import com.zx.lib.http.kit.HttpKit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/28 19:28
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class BusRealEstateServiceTest {

    @Test
    public void test1() {
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wx4476c55348a31df8");
        param.put("secret", "5f1bde7126625315c1c045b03979ba6a");
        param.put("js_code", "test");
        param.put("grant_type", "authorization_code");
        HttpEntity httpEntity = HttpKit.get("https://api.weixin.qq.com/sns/jscode2session", param, true);
        final String html = httpEntity.getHtml();
        System.out.println(html);
    }
}