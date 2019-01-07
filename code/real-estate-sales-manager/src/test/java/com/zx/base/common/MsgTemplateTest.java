package com.zx.base.common;

import com.alibaba.fastjson.JSON;
import com.zx.business.notify.model.YunzhixunSmsMessage;
import com.zx.lib.http.common.HttpConst;
import com.zx.lib.http.entity.HttpEntity;
import com.zx.lib.http.kit.HttpKit;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2019/1/2 19:05
 */
public class MsgTemplateTest {

    @Test
    public void testAddTemplate() {
        String url = "http://open.ucpaas.com/ol/sms/addsmstemplate";
        Map<String, String> header = new HashMap<>();
        header.put(HttpConst.CONTENT_TYPE, "application/json");
        Map<String, String> params = new HashMap<>();
        params.put("sid", "dc0cc5d6997208a7f0cf35f02de9c48c");
        params.put("token", "eb115ffdb7cab2c14e26f862b5be7ccd");
        params.put("appid", "95c4984c0c7240eca3684e27bf29cad7");
        params.put("type", "0");
        params.put("template_name", "安策系统模板");
        params.put("autograph", "安策系统");
        params.put("content", "您好，有一条您的{1}消息，联系人：{2}，订单号：{3}。详情请查看安策营销系统！");

        HttpEntity httpEntity = HttpKit.post(url, null, JSON.toJSONString(params), header, Charset.defaultCharset());
        System.out.println(httpEntity.getHtml());
    }

    @Test
    public void test1() {
        YunzhixunSmsMessage smsMessage = new YunzhixunSmsMessage();
        smsMessage.setAppid("appid");
        smsMessage.setMobile("12031239213");
        smsMessage.setParam("sdfasdf");
        smsMessage.setTemplateid("234dsf32r23");
        smsMessage.setToken("token");
        smsMessage.setUid("uid");
        System.out.println(JSON.toJSONString(smsMessage));
    }

    @Test
    public void testSendMessage() {
        String url = "https://open.ucpaas.com/ol/sms/sendsms";
        Map<String, String> params = new HashMap<>();
        params.put("sid", "dc0cc5d6997208a7f0cf35f02de9c48c");
        params.put("token", "eb115ffdb7cab2c14e26f862b5be7ccd");
        params.put("appid", "95c4984c0c7240eca3684e27bf29cad7");
        params.put("templateid", "417477");
        params.put("param", "报备通知,张先生,153****1902,中岳中介公司,唐僧,19920392183,无");
        params.put("mobile", "15395158022");
        params.put("uid", "");

        Map<String, String> header = new HashMap<>();
        header.put(HttpConst.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = HttpKit.post(url, null, JSON.toJSONString(params), header, Charset.defaultCharset());
        System.out.println(httpEntity.getHtml());
    }

    @Test
    public void testGetTemplate() {
        String url = "https://open.ucpaas.com/ol/sms/getsmstemplate";
        Map<String, String> params = new HashMap<>();
        params.put("sid", "dc0cc5d6997208a7f0cf35f02de9c48c");
        params.put("token", "eb115ffdb7cab2c14e26f862b5be7ccd");
        params.put("appid", "95c4984c0c7240eca3684e27bf29cad7");
        params.put("templateid", "417477");

        Map<String, String> header = new HashMap<>();
        header.put(HttpConst.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = HttpKit.post(url, null, JSON.toJSONString(params), header, Charset.defaultCharset());
        System.out.println(httpEntity.getHtml());
    }
}
