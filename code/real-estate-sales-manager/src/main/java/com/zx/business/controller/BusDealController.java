package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.common.Const;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusDeal;
import com.zx.business.model.BusRealEstate;
import com.zx.business.service.BusDealService;
import com.zx.business.vo.BusDealVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/busDeal")
public class BusDealController {

    private static Logger logger = LoggerFactory.getLogger(BusDealController.class);

    @Resource
    private BusDealService busDealService;

    @RequestMapping("/getById/{id}")
    @ResponseBody
    @AuthorizeIgnore
    public ResultData getById(@PathVariable Integer id) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取订单详细信息成功！");
        try {
            BusDeal busDeal = busDealService.getById(id);
            resultData.setData(JSON.toJSONString(busDeal));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取订单详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    /**
     * 报备
     * @param busDealVO
     * @return
     */
    @RequestMapping("/report")
    @ResponseBody
    @AuthorizeIgnore
    public ResultData report(@RequestBody BusDealVO busDealVO) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取订单详细信息成功！");
        try {
            busDealService.report(busDealVO);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取订单详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    /**
     * 预约
     * @param busDealVO
     * @return
     */
    @RequestMapping("/appointment")
    @ResponseBody
    @AuthorizeIgnore
    public ResultData appointment(@RequestBody BusDealVO busDealVO) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取订单详细信息成功！");
        try {
            resultData.setData(JSON.toJSONString(""));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取订单详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    /**
     * 到访
     * @param busDealVO
     * @return
     */
    @RequestMapping("/arrive")
    @ResponseBody
    @AuthorizeIgnore
    public ResultData arrive(@RequestBody BusDealVO busDealVO) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取订单详细信息成功！");
        try {
            resultData.setData(JSON.toJSONString(""));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取订单详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    /**
     * 认购
     * @param busDealVO
     * @return
     */
    @RequestMapping("/subscribe")
    @ResponseBody
    @AuthorizeIgnore
    public ResultData subscribe(@RequestBody BusDealVO busDealVO) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取订单详细信息成功！");
        try {
            resultData.setData(JSON.toJSONString(""));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取订单详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }


}
