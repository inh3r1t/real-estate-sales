package com.zx.business.controller;

import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.common.Const;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusAgentCompany;
import com.zx.business.model.BusDeal;
import com.zx.business.service.BusAgentCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 14:03
 */
@Controller
@RequestMapping("busAgentCompany")
public class BusAgentCompanyController {

    private static Logger logger = LoggerFactory.getLogger(BusAgentCompanyController.class);

    @Resource
    private BusAgentCompanyService busAgentCompanyService;

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public ResultData getList(@RequestBody BusAgentCompany busAgentCompany) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取中介公司列表成功！");
        try {
            List<BusAgentCompany> busDealList = busAgentCompanyService.getList(busAgentCompany);
            resultData.setData(busDealList);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取客户列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    public ResultData getPage(@RequestBody BusAgentCompany busAgentCompany) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取中介公司列表成功！");
        try {
            PagerModel<BusAgentCompany> busDealPage = busAgentCompanyService.getPage(busAgentCompany.getPage(), busAgentCompany.getPageSize(), busAgentCompany);
            resultData.setData(busDealPage);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取客户列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ResultData addOrUpdate(@RequestBody BusAgentCompany busAgentCompany) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "新增更新中介公司成功！");
        try {
            BusAgentCompany result = busAgentCompanyService.addOrUpdate(busAgentCompany);
            resultData.setData(result);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取客户列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delete(@RequestBody Integer id) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "删除中介公司成功！");
        try {
            busAgentCompanyService.delete(id);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取客户列表失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }
}
