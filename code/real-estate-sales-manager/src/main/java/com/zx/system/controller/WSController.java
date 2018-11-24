package com.zx.system.controller;

import com.zx.base.model.WsResponseModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * WebSocket 控制器
 *
 * @author Xiafl.
 * @version 2017/12/12
 */
@Controller
public class WSController {
    /**
     * 登录
     */
    @MessageMapping("/connect")
    @SendTo(value = "/meta/onlineCnt")
    public WsResponseModel connect(String body) {
        throw new NotImplementedException();
    }
}
