package com.zx.business.notify;

import com.alibaba.fastjson.JSON;
import com.zx.business.notify.model.Message;
import com.zx.business.notify.model.WechatMessage;
import com.zx.lib.http.common.HttpConst;
import com.zx.lib.http.kit.HttpKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ytxu3
 * @Description: 微信通知渠道
 * @Date: 2019/1/2 17:01
 */
@Component("wechatNotify")
public class WechatNotify implements Notify {

    @Value("${custom.wechat.access_token.api.url}")
    String accessTokenApiUrl;

    @Value("${custom.wechat.send_template_message.api.url}")
    String sendTemplateMessageUrl;

    @Value("${custom.wechat.appid}")
    String appid;

    @Value("${custom.wechat.secret}")
    String secret;

    @Override
    public String notify(Message message) {
        String sendMsgUrl = sendTemplateMessageUrl + getAccessToken();
        Map<String, String> header = new HashMap<>();
        header.put(HttpConst.CONTENT_TYPE, "application/json");
        String result = HttpKit.post(sendMsgUrl, null, JSON.toJSONString(message), header, Charset.defaultCharset()).getHtml();
        return result;
    }

    private String getAccessToken() {
        String token = JSON.parseObject(HttpKit.get(String.format(accessTokenApiUrl, "client_credential", appid, secret))
                .getHtml()).get("access_token").toString();
        return token;
    }
}
