package com.zx.business.controller;

import com.alibaba.fastjson.JSON;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.base.model.ReturnModel;
import com.zx.business.model.BusRealEstate;
import com.zx.business.service.BusRealEstateService;
import com.zx.lib.utils.DateUtil;
import com.zx.system.model.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/edit")
    public String edit(Model model, Integer id) {
        BusRealEstate estate = new BusRealEstate();
        if (id != null && !id.equals(0)) {
            estate = busRealStateService.getById(id);
        } else {
        }
        model.addAttribute("model", estate);
        return "business/busRealEstate/edit";
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public Object submit(BusRealEstate estate) {

        boolean insertAction = estate.getId() == null || estate.getId() == 0;

        String actionName = insertAction ? "添加" : "修改";
        if (insertAction) {
            estate.setCreateTime(DateUtil.getDateNow());
            busRealStateService.add(estate);
        } else {
            busRealStateService.update(estate);
        }
        try {
            addLogInfo(String.format("%s活动%s成功", actionName, estate.getName()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改);
            return new ReturnModel(true, String.format("%s成功", actionName));
        } catch (Exception e) {
            addLogError(String.format("%s活动%s失败", actionName, estate.getName()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改, e, estate);
            return new ReturnModel(false, String.format("%s失败", actionName));
        }
    }
}
