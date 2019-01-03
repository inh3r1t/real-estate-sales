package com.zx.business.notify;

import com.alibaba.fastjson.JSON;
import com.zx.business.notify.model.Message;
import com.zx.business.notify.model.YunzhixunSmsMessage;
import com.zx.lib.http.common.HttpConst;
import com.zx.lib.http.kit.HttpKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2019/1/2 17:11
 */
@Component("yunzhixunMessageNotify")
public class YunzhixunSmsNotify extends SmsNotify {

    @Value("custom.yunzhixun.appid")
    private String appid;

    @Value("custom.yunzhixun.sid")
    private String sid;

    @Value("custom.yunzhixun.token")
    private String token;

    @Value("custom.yunzhixun.sms.send.url")
    private String sendUrl;

    @Value("custom.yunzhixun.sms.send.templateid")
    private String templateid;

    @Override
    public String notify(Message message) {
        YunzhixunSmsMessage smsMessage = (YunzhixunSmsMessage) message;
        smsMessage.setUid("");
        smsMessage.setToken(token);
        smsMessage.setAppid(appid);
        smsMessage.setSid(sid);
        smsMessage.setTemplateid(templateid);
        Map<String, String> header = new HashMap<>();
        header.put(HttpConst.CONTENT_TYPE, "application/json");
        String result = HttpKit.post(sendUrl, null, JSON.toJSONString(message), header, Charset.defaultCharset()).getHtml();
        return result;
    }
}
