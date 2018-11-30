package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/busUser")
public class BusUserController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BusUserController.class);

    @Resource
    private BusUserService busUserService;

    /*
     * @Title: register
     * @Description: 用户注册
     * @params [busUser]
     * @return com.zx.base.model.ResultData
     * @date 2018/11/28 11:36
     * @throws
     */
    @AuthorizeIgnore
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultData register(@RequestBody BusUser busUser) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "用户注册成功！");
        try {
            int id = busUserService.addBusUser(busUser);
            resultData.setData(JSON.toJSONString(id));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("用户注册失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    public ResultData login(@RequestBody BusUser busUser) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "用户登录成功！");
        try {
            BusUser user = busUserService.login(busUser);
            resultData.setData(JSON.toJSONString(user));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("用户登录失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }
}
