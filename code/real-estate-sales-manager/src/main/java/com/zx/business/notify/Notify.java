package com.zx.business.notify;

import com.zx.business.notify.model.Message;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2019/1/2 16:56
 */
public interface Notify {

    String notify(Message message);
}
