package com.zx.business.notify;

import com.alibaba.fastjson.JSON;
import com.zx.business.notify.model.Message;
import com.zx.lib.http.kit.HttpKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        String result = HttpKit.post(sendMsgUrl, "").getHtml();
        return result;
    }

    private String getAccessToken() {
        return JSON.parseObject(HttpKit.get(String.format(accessTokenApiUrl, "client_credential", appid, secret))
                .getHtml()).get("access_token").toString();
    }
}
