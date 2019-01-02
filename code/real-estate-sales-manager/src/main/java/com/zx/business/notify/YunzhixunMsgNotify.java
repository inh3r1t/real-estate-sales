package com.zx.business.notify;

import com.zx.business.notify.model.Message;
import org.springframework.stereotype.Component;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2019/1/2 17:11
 */
@Component("yunzhixunMessageNotify")
public class YunzhixunMsgNotify extends MsgNotify {

    @Override
    public String notify(Message message) {
        return null;
    }
}
