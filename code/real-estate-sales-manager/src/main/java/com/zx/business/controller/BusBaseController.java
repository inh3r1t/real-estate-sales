package com.zx.business.controller;

import com.zx.base.exception.BusinessException;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusUserService;

import javax.annotation.Resource;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/28 22:00
 */
public abstract class BusBaseController {

    @Resource
    private BusUserService busUserService;

    public BusUser validateToken(String token) {
        if (token == null)
            throw new BusinessException("请先登录！");
        return busUserService.getUserFromToken(token);
    }
}
