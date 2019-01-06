package com.zx.business.controller;

import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.base.model.ReturnModel;
import com.zx.business.model.BusAgentCompany;
import com.zx.business.model.BusDeal;
import com.zx.business.service.BusAgentCompanyService;
import com.zx.system.model.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 14:03
 */
@Controller
@RequestMapping("/busAgentCompany")
public class BusAgentCompanyController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BusAgentCompanyController.class);

    @Resource
    private BusAgentCompanyService busAgentCompanyService;

    // @RequestMapping(value = "/getList", method = RequestMethod.POST)
    // @ResponseBody
    // public ResultData getList(@RequestBody BusAgentCompany busAgentCompany) {
    //     ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取中介公司列表成功！");
    //     try {
    //         List<BusAgentCompany> busDealList = busAgentCompanyService.getList(busAgentCompany);
    //         resultData.setData(busDealList);
    //     } catch (Exception e) {
    //         resultData.setResultCode(Const.FAILED_CODE);
    //         resultData.setMsg("获取客户列表失败！");
    //         logger.error(e.getMessage(), e);
    //     }
    //     return resultData;
    // }

    @RequestMapping("/list")
    public String list() {
        return "business/busAgentCompany/list";
    }


    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(BusAgentCompany busAgentCompany) {
        try {
            if (busAgentCompany.getPage() == null) {
                busAgentCompany.setPage(1);
            }
            if (busAgentCompany.getPageSize() == null) {
                busAgentCompany.setPageSize(PAGE_SIZE);
            }
            return busAgentCompanyService.getPage(busAgentCompany.getPage(), busAgentCompany.getPageSize(), busAgentCompany);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PagerModel<>(busAgentCompany.getPage(), busAgentCompany.getPageSize(), 0, new ArrayList<>());
        }
    }


    @RequestMapping("/edit")
    public String edit(Model model, Integer id) {
        BusAgentCompany company = new BusAgentCompany();
        if (id != null && !id.equals(0)) {
            company = busAgentCompanyService.getById(id);
        }
        model.addAttribute("model", company);
        return "business/busAgentCompany/edit";
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public Object submit(BusAgentCompany busAgentCompany) {
        boolean insertAction = busAgentCompany.getId() == null || busAgentCompany.getId() == 0;
        String actionName = insertAction ? "添加" : "更新";
        try {
            BusAgentCompany result = busAgentCompanyService.addOrUpdate(busAgentCompany);
            addLogInfo(String.format("%s中介公司%s成功", actionName, busAgentCompany.getName()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改);
            return new ReturnModel(true, String.format("%s成功", actionName));
        } catch (Exception e) {
            addLogError(String.format("%s中介公司%s失败", actionName, busAgentCompany.getName()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改, e, busAgentCompany);
            return new ReturnModel(false, String.format("%s失败", actionName));
        }
    }

    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    @WechatAuthorize
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

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(Integer id) {
        ReturnModel rm = new ReturnModel();
        try {
            busAgentCompanyService.delete(id);
            rm.setInfo(true, "删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setInfo(false, "删除失败");
        }
        return rm;
    }
}
