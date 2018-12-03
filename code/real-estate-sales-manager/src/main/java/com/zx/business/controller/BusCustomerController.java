package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.common.Const;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusCustomer;
import com.zx.business.service.BusCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/29 23:43
 */
@Controller
@RequestMapping("/busCustomer")
public class BusCustomerController {

    private static Logger logger = LoggerFactory.getLogger(BusCustomerController.class);

    @Resource
    private BusCustomerService busCustomerService;

    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    @AuthorizeIgnore
    public ResultData getPage(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestBody BusCustomer busCustomer) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取客户列表成功！");
        try {
            PagerModel<BusCustomer> busCustomerPage = busCustomerService.getPage(page, pageSize, busCustomer);
            resultData.setData(JSON.toJSONString(busCustomerPage));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取客户列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    @AuthorizeIgnore
    public ResultData getList(@RequestBody BusCustomer busCustomer) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取客户列表成功！");
        try {
            List<BusCustomer> busCustomers = busCustomerService.getList(busCustomer);
            resultData.setData(JSON.toJSONString(busCustomers));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取客户列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

}
