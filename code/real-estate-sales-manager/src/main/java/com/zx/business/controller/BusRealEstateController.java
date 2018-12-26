package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusRealEstate;
import com.zx.business.service.BusRealEstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private BusRealEstateService busRealStateService;

    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
//    @WechatAuthorize
    @AuthorizeIgnore
    public ResultData getPage(@RequestBody BusRealEstate busRealEstate) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘列表成功！");
        try {
            PagerModel<BusRealEstate> busRealEstatePage =
                    busRealStateService.getPage(busRealEstate.getPage(), busRealEstate.getPageSize(), busRealEstate);
            resultData.setData(busRealEstatePage);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
//    @WechatAuthorize
    @AuthorizeIgnore
    public ResultData getList(@RequestBody BusRealEstate busRealEstate) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘列表成功！");
        try {
            List<BusRealEstate> busRealEstates = busRealStateService.getList(busRealEstate);
            resultData.setData(busRealEstates);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    @AuthorizeIgnore
    public ResultData getById(@PathVariable Integer id) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取楼盘详细信息成功！");
        try {
            BusRealEstate busRealEstate = busRealStateService.getById(id);
            resultData.setData(busRealEstate);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取楼盘详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    public ResultData add(BusRealEstate busRealEstate) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "新增楼盘信息成功！");
        try {
            int id = busRealStateService.add(busRealEstate);
            resultData.setData(busRealEstate);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("新增楼盘信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }
}
