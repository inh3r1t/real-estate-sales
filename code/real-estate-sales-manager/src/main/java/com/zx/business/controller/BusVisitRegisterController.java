package com.zx.business.controller;

import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusRealEstate;
import com.zx.business.model.BusVisitRegister;
import com.zx.business.service.BusUserService;
import com.zx.business.service.BusVisitRegisterService;
import com.zx.lib.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author weiyi
 */
@Controller
@RequestMapping("/busVisitRegister")
public class BusVisitRegisterController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BusVisitRegisterController.class);
    @Resource
    private BusVisitRegisterService busVisitRegisterService;

    @RequestMapping("/list")
    public String list() {
        return "business/busVisitRegister/list";
    }


    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    @WechatAuthorize
    public ResultData getPage(@RequestBody BusVisitRegister model, String time) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取列表成功！");
        try {
            String startDateTime = null, endDateTime = null;
            if (StringUtils.isNotEmpty(time)) {
                String[] seTime = time.split(" - ");
                if (seTime.length > 1) {
                    startDateTime = seTime[0].trim();
                    endDateTime = seTime[1].trim();

                }
            }
            PagerModel<BusVisitRegister> busRealEstatePage =
                    busVisitRegisterService.getPage(model.getPage(), model.getPageSize(), startDateTime, endDateTime);
            resultData.setData(busRealEstatePage);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(BusVisitRegister model, String time) {
        try {
            if (model.getPage() == null) {
                model.setPage(1);
            }
            if (model.getPageSize() == null) {
                model.setPageSize(PAGE_SIZE);
            }
            String startDateTime = null, endDateTime = null;
            if (StringUtils.isNotEmpty(time)) {
                String[] seTime = time.split(" - ");
                if (seTime.length > 1) {
                    startDateTime = seTime[0].trim();
                    endDateTime = seTime[1].trim();

                }
            }
            return busVisitRegisterService.getPage(model.getPage(), model.getPageSize(), startDateTime, endDateTime);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PagerModel<>(model.getPage(), model.getPageSize(), 0, new ArrayList<>());
        }
    }


}
