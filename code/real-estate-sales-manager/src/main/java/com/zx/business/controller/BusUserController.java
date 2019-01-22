package com.zx.business.controller;

import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.common.Const;
import com.zx.base.exception.BusinessException;
import com.zx.base.exception.WechatAuthException;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusUserService;
import com.zx.business.vo.BusUserVO;
import com.zx.lib.utils.encrypt.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/busUser")
public class BusUserController extends BusBaseController {

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
            // 验证手机号是否注册
            BusUser searchUser = new BusUser();
            searchUser.setPhoneNum(busUser.getPhoneNum());
            searchUser = busUserService.getBusUser(searchUser);
            if (searchUser != null) {
                resultData.setResultCode(Const.USER_EXISTED_ERROR);
                resultData.setMsg("该手机号已经被注册！");
            } else {
                int id = busUserService.addBusUser(busUser);
                resultData.setData(id);
            }
        } catch (Exception e) {
            if (e instanceof WechatAuthException) {
                resultData.setResultCode(e.getMessage());
            } else {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg(e.getMessage());
            }
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @AuthorizeIgnore
    public ResultData login(@RequestBody BusUserVO busUserVO) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "用户登录成功！");
        try {
            Map<String, Object> result;
            if (StringUtils.isNotEmpty(busUserVO.getJs_code())) {
                String openid = getOpenid(busUserVO.getJs_code());
                result = busUserService.loginByWechat(openid);
            } else if (StringUtils.isNotEmpty(busUserVO.getPhoneNum()) && StringUtils.isNotEmpty(busUserVO.getPasswd())) {
                result = busUserService.loginByAccount(busUserVO.getPhoneNum(), busUserVO.getPasswd());
            } else
                throw new BusinessException(Const.NO_EXIST_USER);
            resultData.setData(result);
        } catch (Exception e) {
            resultData.setResultCode(e.getMessage());
            resultData.setMsg("用户登录失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }


    @AuthorizeIgnore
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultData update(@RequestBody BusUserVO busUserVO) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "操作成功！");
        try {
            // 验证手机号是否注册
            BusUser busUser = new BusUser();
            String token = request.getHeader("token");
            busUser.setId(getUserByToken(token).getId());
            busUser = busUserService.getBusUser(busUser);
            if (StringUtils.isNotEmpty(busUserVO.getJs_code())) {
                String openid = getOpenid(busUserVO.getJs_code());
                // 判断该微信号是否已被绑定
                BusUser condition = new BusUser();
                condition.setOpenId(openid);
                if (null != busUserService.getBusUser(condition)) {
                    resultData.setResultCode(Const.WECHAT_ALREADY_BIND_ERROR);
                    resultData.setMsg("该微信已绑定过其他账号！");
                    return resultData;
                }
                busUser.setOpenId(openid);
            } else if (StringUtils.isEmpty(busUserVO.getPasswd())) {
                busUser.setOpenId("");
            }
            if (StringUtils.isNotEmpty(busUserVO.getPasswd())) {
                busUser.setPasswd(Md5Util.getMd5(busUserVO.getPasswd()));
            }
            busUserService.updateByPrimaryKeySelective(busUser);
            resultData.setData(busUser);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("操作失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "getListByRoleId", method = RequestMethod.POST)
    @ResponseBody
    public ResultData getListByRoleId(@RequestBody Integer roleType) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "用户登录成功！");
        try {
            List<BusUser> busUserList = busUserService.getListByRoleType(roleType);
            resultData.setData(busUserList);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("用户登录失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @AuthorizeIgnore
    @RequestMapping(value = "getVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultData getVerifyCode(String phoneNum) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取验证码成功！");
        try {
            String verifyCode = busUserService.getVerifyCode(phoneNum);
            resultData.setData(verifyCode);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取验证码失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @AuthorizeIgnore
    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public ResultData sendMessage(String phoneNum) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "发送验证码成功！");
        try {
            String code = busUserService.sendMessage(phoneNum);
            if (code != null) {
                resultData.setResultCode(code);
                resultData.setData("验证码未过期！");
            }
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("发送验证码失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }
}
