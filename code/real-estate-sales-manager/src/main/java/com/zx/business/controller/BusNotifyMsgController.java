package com.zx.business.controller;

import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusNotifyMsg;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusNotifyMsgService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 14:04
 */
@Controller
@RequestMapping("busNotifyMsg")
public class BusNotifyMsgController extends BusBaseController {

    @Resource
    private BusNotifyMsgService busNotifyMsgService;

    @RequestMapping(value = "addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ResultData addOrUpdate(@RequestBody BusNotifyMsg condition) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "通知新增更新成功！");
        try {
            BusNotifyMsg busNotifyMsg = busNotifyMsgService.addOrUpdate(condition);
            resultData.setData(busNotifyMsg);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("通知新增更新失败！");
        }
        return resultData;
    }

    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    @ResponseBody
    public ResultData deleteById(@RequestBody BusNotifyMsg condition) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "通知删除成功！");
        try {
            busNotifyMsgService.deleteById(condition.getId());
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("通知删除失败！");
        }
        return resultData;
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    @ResponseBody
    public ResultData getList(@RequestBody BusNotifyMsg condition) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取通知列表成功！");
        try {
            List<BusNotifyMsg> busNotifyMsgs = busNotifyMsgService.getList(condition);
            resultData.setData(busNotifyMsgs);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取通知列表失败！");
        }
        return resultData;
    }

    @RequestMapping(value = "getPage", method = RequestMethod.POST)
    @ResponseBody
    @WechatAuthorize
    public ResultData getPage(@RequestBody BusNotifyMsg condition, HttpServletRequest request) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取通知列表成功！");
        try {
            wechatAuthCheck(request);
            BusUser currentUser = getUserByToken(request.getHeader("token"));
            condition.setReceiveUserId(currentUser.getId());
            PagerModel<BusNotifyMsg> busNotifyMsgs = busNotifyMsgService.getPage(condition.getPage(), condition.getPageSize(), condition);
            resultData.setData(busNotifyMsgs);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取通知列表失败！");
        }
        return resultData;
    }


}
