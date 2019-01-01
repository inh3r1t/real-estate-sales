package com.zx.business.controller;

import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.exception.WechatAuthException;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusDeal;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusDealService;
import com.zx.business.vo.BusDealVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/busDeal")
public class BusDealController extends BusBaseController {

    private static Logger logger = LoggerFactory.getLogger(BusDealController.class);

    @Resource
    private BusDealService busDealService;

    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    @WechatAuthorize
    public ResultData getPage(@RequestBody BusDeal condition, HttpServletRequest request) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取客户列表成功！");
        try {
            wechatAuthCheck(request);

            BusUser currentUser = getUserByToken(request.getHeader("token"));
            BusDeal busDeal = new BusDeal();
            if (currentUser.getBusRole().getType().equals("0")) {
                // 驻场经理
                condition.setManagerId(currentUser.getId());
                busDeal.setManagerId(currentUser.getId());
            } else {
                // 中介
                condition.setReportUserId(currentUser.getId());
                busDeal.setReportUserId(currentUser.getId());
            }
            PagerModel<BusDeal> busDealPage = busDealService.getPage(condition.getPage(), condition.getPageSize(), condition);
            Map<String, Long> countByState = busDealService.countByState(busDeal);
            Map<String, Object> result = new HashMap<>();
            result.put("list", busDealPage);
            result.put("count", countByState);
            resultData.setData(result);
        } catch (Exception e) {
            if (e instanceof WechatAuthException) {
                resultData.setResultCode(e.getMessage());
            } else {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg("获取客户列表失败！");
            }
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    @AuthorizeIgnore
    public ResultData getList(@RequestBody BusDeal busDeal) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取客户列表成功！");
        try {
            List<BusDeal> busDealList = busDealService.getList(busDeal);
            resultData.setData(busDealList);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取客户列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping("/getById/{id}")
    @ResponseBody
    @WechatAuthorize
    @AuthorizeIgnore
    public ResultData getById(@PathVariable Integer id) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取订单详细信息成功！");
        try {
            BusDeal busDeal = busDealService.getById(id);
            resultData.setData(busDeal);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取订单详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    /**
     * 报备
     *
     * @param busDealVO
     * @return
     */
    @RequestMapping("/report")
    @ResponseBody
    @WechatAuthorize
    public ResultData report(@RequestBody BusDealVO busDealVO, HttpServletRequest request) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "报备成功！");
        try {
            wechatAuthCheck(request);

            String token = request.getHeader("token");
            busDealVO.setReportUserId(getUserByToken(token).getId());
            busDealService.report(busDealVO, getAccessToken());
        } catch (Exception e) {
            if (e instanceof WechatAuthException) {
                resultData.setResultCode(e.getMessage());
            } else {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg("获取订单详细信息失败！");
            }
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    /**
     * 预约
     *
     * @param BusDeal
     * @return
     */
    @RequestMapping("/appointment")
    @ResponseBody
    @WechatAuthorize
    public ResultData appointment(@RequestBody BusDeal BusDeal) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "订单预约成功！");
        try {
            wechatAuthCheck(request);

            final BusDeal result = busDealService.appointment(BusDeal);
            resultData.setData(result);
        } catch (Exception e) {
            if (e instanceof WechatAuthException) {
                resultData.setResultCode(e.getMessage());
            } else {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg("订单预约失败！");
            }
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    /**
     * 到访
     *
     * @param busDeal
     * @return
     */
    @RequestMapping("/arrive")
    @ResponseBody
    @WechatAuthorize
    public ResultData arrive(@RequestBody BusDeal busDeal) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "客户到访成功！");
        try {
            wechatAuthCheck(request);

            final BusDeal result = busDealService.arrive(busDeal);
            resultData.setData(result);
        } catch (Exception e) {
            if (e instanceof WechatAuthException) {
                resultData.setResultCode(e.getMessage());
            } else {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg("客户到访失败！");
            }
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    /**
     * 认购
     *
     * @param busDeal
     * @return
     */
    @RequestMapping("/subscribe")
    @ResponseBody
    @WechatAuthorize
    public ResultData subscribe(@RequestBody BusDeal busDeal) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "认购成功！");
        try {
            wechatAuthCheck(request);

            final BusDeal result = busDealService.subscribe(busDeal);
            resultData.setData(result);
        } catch (Exception e) {
            if (e instanceof WechatAuthException) {
                resultData.setResultCode(e.getMessage());
            } else {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg("认购失败！");
            }
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }


}
