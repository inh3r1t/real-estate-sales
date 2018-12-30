package com.zx.business.controller;

import com.zx.business.service.BusNotifyMsgService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 14:04
 */
@Controller
@RequestMapping("busNotifyMsg")
public class BusNotifyMsgController {

    @Resource
    private BusNotifyMsgService busNotifyMsgService;
}
