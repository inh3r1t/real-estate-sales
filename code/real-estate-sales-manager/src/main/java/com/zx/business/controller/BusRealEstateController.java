package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.model.PageCondition;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusRealEstate;
import com.zx.business.service.BusRealEstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/28 18:50
 */
@Controller
@RequestMapping("/busRealEstate")
public class BusRealEstateController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BusRealEstateController.class);

    @Resource
    private BusRealEstateService busRealStateSevice;

    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    public ResultData getPage(PageCondition condition) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘列表成功！");
        try {
            PagerModel<BusRealEstate> busRealEstatePage = busRealStateSevice.getList(condition);
            resultData.setData(JSON.toJSONString(busRealEstatePage));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public ResultData getById(Integer id) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘详细信息成功！");
        try {
            BusRealEstate busRealEstate = busRealStateSevice.getById(id);
            resultData.setData(JSON.toJSONString(busRealEstate));
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }
}
