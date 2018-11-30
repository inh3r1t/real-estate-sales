package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.common.Const;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusRealEstate;
import com.zx.business.service.BusCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/29 23:43
 */
@Controller
@RequestMapping("busCustomer")
public class BusCustomerController {

    private static Logger logger = LoggerFactory.getLogger(BusCustomerController.class);

    @Resource
    private BusCustomerService busCustomerService;

    @RequestMapping(value = "/getPageByAgentId", method = RequestMethod.POST)
    @ResponseBody
    public ResultData getByAgentId() {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘列表成功！");
        try {
            resultData.setData(JSON.toJSONString(""));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

}
