package com.zx.business.controller;

import com.zx.base.controller.BaseController;
import com.zx.base.exception.BusinessException;
import com.zx.base.exception.WechatAuthException;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/28 22:00
 */
public abstract class BusBaseController extends BaseController {

    @Resource
    private BusUserService busUserService;

    public BusUser getUserByToken(String token) {
        if (token == null)
            throw new BusinessException("请先登录！");
        return busUserService.getUserFromToken(token);
    }

    public void wechatAuthCheck(HttpServletRequest request) {
        Object error_code = request.getAttribute("ERROR_CODE");
        if (error_code != null)
            throw new WechatAuthException(error_code.toString());
    }
}
