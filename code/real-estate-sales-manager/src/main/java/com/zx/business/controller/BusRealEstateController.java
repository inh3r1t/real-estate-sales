package com.zx.business.controller;

import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.controller.BaseController;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.base.model.ReturnModel;
import com.zx.business.enums.PhotoType;
import com.zx.business.model.BusRealEstate;
import com.zx.business.model.BusUser;
import com.zx.business.service.BusRealEstateService;
import com.zx.business.service.BusUserService;
import com.zx.lib.utils.DateUtil;
import com.zx.lib.utils.StringUtil;
import com.zx.lib.web.HtmlUtil;
import com.zx.system.model.FileInfo;
import com.zx.system.model.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private BusUserService busUserService;

    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    @WechatAuthorize
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
    public Object getList(BusRealEstate busRealEstate) {
        try {
            if (busRealEstate.getPage() == null) {
                busRealEstate.setPage(1);
            }
            if (busRealEstate.getPageSize() == null) {
                busRealEstate.setPageSize(PAGE_SIZE);
            }
            return busRealStateService.getPage(busRealEstate.getPage(), busRealEstate.getPageSize(), busRealEstate);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PagerModel<>(busRealEstate.getPage(), busRealEstate.getPageSize(), 0, new ArrayList<>());
        }
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    @WechatAuthorize
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

    @RequestMapping("/list")
    public String list() {
        return "business/busRealEstate/list";
    }


    @RequestMapping("/edit")
    public String edit(Model model, Integer id) {
        BusRealEstate estate = new BusRealEstate();
        if (id != null && !id.equals(0)) {
            estate = busRealStateService.getById(id);
        }
        List<BusUser> managerList = new ArrayList<>();
        managerList = busUserService.getListByRoleType(0);
        model.addAttribute("model", estate);
        model.addAttribute("managerList", managerList);
        return "business/busRealEstate/edit";
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public Object submit(BusRealEstate estate) {
        boolean insertAction = estate.getId() == null || estate.getId() == 0;
        String actionName = insertAction ? "添加" : "更新";
        estate.setDetail(HtmlUtil.htmlUnescape(estate.getDetail()));
        estate.setCommission(estate.getCommission().replaceAll("%2B", "+"));
        try {
            if (insertAction) {
                estate.setCreateTime(DateUtil.getDateNow());
                busRealStateService.add(estate);
            } else {
                busRealStateService.update(estate);
                fileInfoService.deleteByGroupId(estate.getId());
            }
            //处理图片
            List<FileInfo> fileInfos = new ArrayList<>();
            FileInfo fileInfo;
            //滚动图
            if (estate.getImages() != null) {
                for (String url : estate.getImages()) {
                    fileInfo = new FileInfo();
                    fileInfo.setGroupid(estate.getId());
                    fileInfo.setPath(url);
                    fileInfo.setRemark(PhotoType.DETAIL_TOP.code());
                    fileInfo.setState(0);
                    fileInfos.add(fileInfo);
                }
            }
            //缩略图
            if (StringUtil.isNotEmpty(estate.getThumbnail())) {
                fileInfo = new FileInfo();
                fileInfo.setGroupid(estate.getId());
                fileInfo.setPath(estate.getThumbnail());
                fileInfo.setRemark(PhotoType.THUMBNAIL.code());
                fileInfo.setState(0);
                fileInfos.add(fileInfo);
            }
            //推荐图
            if (StringUtil.isNotEmpty(estate.getTopRecommendImage())) {
                fileInfo = new FileInfo();
                fileInfo.setGroupid(estate.getId());
                fileInfo.setPath(estate.getTopRecommendImage());
                fileInfo.setRemark(PhotoType.MAIN_PAGE_TOP.code());
                fileInfo.setState(0);
                fileInfos.add(fileInfo);
            }
            if (fileInfos.size() > 0) {
                fileInfoService.insertBatch(fileInfos);
            }
            addLogInfo(String.format("%s楼盘%s成功", actionName, estate.getName()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改);
            return new ReturnModel(true, String.format("%s成功", actionName));
        } catch (Exception e) {
            addLogError(String.format("%s楼盘%s失败", actionName, estate.getName()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改, e, estate);
            return new ReturnModel(false, String.format("%s失败", actionName));
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(Integer id) {
        ReturnModel rm = new ReturnModel();
        try {
            busRealStateService.delete(id);
            rm.setInfo(true, "删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            rm.setInfo(false, "删除失败");
        }
        return rm;
    }
}
