package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.exception.BusinessException;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusUserService;
import com.zx.business.vo.BusUserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @CrossOrigin
    public ResultData register(@RequestBody BusUser busUser) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "用户注册成功！");
        try {
            // 验证手机号是否注册
            BusUser searchUser = new BusUser();
            searchUser.setPhoneNum(busUser.getPhoneNum());
            searchUser = busUserService.getBusUser(searchUser);
            if (searchUser != null) {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg("该手机号已经被注册！");
            } else {
                int id = busUserService.addBusUser(busUser);
                resultData.setData(id);
            }
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("用户注册失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @AuthorizeIgnore
    public ResultData login(@RequestBody BusUserVO busUserVO) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "用户登录成功！");
        try {
            String token = null;
            if (StringUtils.isNotEmpty(busUserVO.getJs_code())) {
                String openid = getOpenid(busUserVO.getJs_code());
                token = busUserService.loginByWechat(openid);
            } else if (StringUtils.isNotEmpty(busUserVO.getPhoneNum()) && StringUtils.isNotEmpty(busUserVO.getPasswd())) {
                token = busUserService.loginByAccount(busUserVO.getPhoneNum(), busUserVO.getPasswd());
            } else
                throw new BusinessException("用户登录失败！");
            resultData.setData(JSON.toJSONString(token));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("用户登录失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }
}
