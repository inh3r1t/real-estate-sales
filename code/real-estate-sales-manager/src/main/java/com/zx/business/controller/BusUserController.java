package com.zx.business.controller;

import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.exception.BusinessException;
import com.zx.base.exception.WechatAuthException;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.base.model.ReturnModel;
import com.zx.business.model.BusUser;
import com.zx.business.model.BusVisitRegister;
import com.zx.business.service.BusUserService;
import com.zx.business.vo.BusUserVO;
import com.zx.lib.utils.StringUtil;
import com.zx.lib.utils.encrypt.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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

    @WechatAuthorize
    @RequestMapping(value = "/reset/{phoneNum}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public ResultData reset(@PathVariable String phoneNum, @PathVariable String password) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "密码重置成功！");
        try {
            busUserService.reset(phoneNum, password);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                resultData.setResultCode(e.getMessage());
                resultData.setMsg("账号不存在，请检查手机号码！");
            } else {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg("密码重置失败！");
            }
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

    @WechatAuthorize
    @RequestMapping(value = "verifyCode/{phoneNum}/{verifyCode}", method = RequestMethod.GET)
    @ResponseBody
    public ResultData verifyCode(@PathVariable String phoneNum, @PathVariable String verifyCode) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取验证码成功！");
        try {
            String realVerifyCode1 = busUserService.getVerifyCode(phoneNum);
            if (!verifyCode.equals(realVerifyCode1)) {
                resultData.setResultCode(Const.VERIFY_ERROR);
                resultData.setMsg("验证码输入错误！");
            }
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取验证码失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @WechatAuthorize
    @RequestMapping(value = "sendMessage/{phoneNum}", method = RequestMethod.GET)
    @ResponseBody
    public ResultData sendMessage(@PathVariable String phoneNum) {
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


    @RequestMapping("/list")
    public String list() {
        return "business/busUser/list";
    }



    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(BusVisitRegister model,
                          String time,
                          String keyword,
                          String phoneNum) {
        try {
            if (model.getPage() == null) {
                model.setPage(1);
            }
            if (model.getPageSize() == null) {
                model.setPageSize(PAGE_SIZE);
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String startDateTime = null, endDateTime = null;
            if (StringUtils.isNotEmpty(time)) {
                String[] seTime = time.split(" - ");
                if (seTime.length > 1) {
                    startDateTime = seTime[0].trim();
                    endDateTime = seTime[1].trim();
                    paramMap.put("startDateTime", startDateTime);
                    paramMap.put("endDateTime", endDateTime);
                }
            }
            if (StringUtil.isNotEmpty(keyword)) {
                paramMap.put("keyword", keyword);
            }
            paramMap.put("orderField", "create_time");
            paramMap.put("orderType", "desc");
            int count = busUserService.selectCount(paramMap);

            paramMap.put("start", (model.getPage() - 1) * model.getPageSize());
            paramMap.put("pageSize", model.getPageSize());
            List<BusUser> list =
                    busUserService.getList(paramMap);
            return new PagerModel<>(model.getPageSize(), model.getPage(), count, list);


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PagerModel<>(model.getPage(), model.getPageSize(), 0, new ArrayList<>());
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(Integer id) {
        ReturnModel rm = new ReturnModel();
        try {
            busUserService.delete(id);
            rm.setInfo(true, "删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setInfo(false, "删除失败");
        }
        return rm;
    }

}
