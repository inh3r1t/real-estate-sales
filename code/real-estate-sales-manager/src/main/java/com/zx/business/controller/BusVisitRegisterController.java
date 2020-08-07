package com.zx.business.controller;

import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.common.ExcelUtil;
import com.zx.base.controller.BaseController;
import com.zx.base.enums.TypeEnums;
import com.zx.base.exception.WechatAuthException;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.base.model.ReturnModel;
import com.zx.business.model.BusNotifyMsg;
import com.zx.business.model.BusRealEstate;
import com.zx.business.model.BusVisitRegister;
import com.zx.business.service.BusUserService;
import com.zx.business.service.BusVisitRegisterService;
import com.zx.business.vo.BusDealVO;
import com.zx.lib.utils.DateUtil;
import com.zx.lib.utils.StringUtil;
import com.zx.system.model.SysLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.util.*;

/**
 * @author weiyi
 */
@Controller
@RequestMapping("/busVisitRegister")
public class BusVisitRegisterController extends BusBaseController {

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

    @RequestMapping("/report")
    @ResponseBody
    @WechatAuthorize
    public ResultData report(@RequestBody BusVisitRegister model, HttpServletRequest request) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "报备成功！");
        try {
            wechatAuthCheck(request);
            if (model.getId() != null && model.getId() > 0) {
                busVisitRegisterService.updateByPrimaryKey(model);
            } else {
                String token = request.getHeader("token");
                model.setCreaterid(getUserByToken(token).getId());
                model.setCreatetime(DateUtil.getDateNow());
                busVisitRegisterService.insert(model);
            }
        } catch (Exception e) {
            if (e instanceof WechatAuthException) {
                resultData.setResultCode(e.getMessage());
            } else {
                resultData.setResultCode(Const.FAILED_CODE);
                resultData.setMsg("登记失败！");
            }
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    @WechatAuthorize
    public ResultData getById(@PathVariable Integer id) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取详细信息成功！");
        try {
            BusVisitRegister model = busVisitRegisterService.getById(id);
            resultData.setData(model);
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("获取详细信息失败！");
            logger.error(e.getMessage(), e);
        }
        return resultData;
    }

    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    @ResponseBody
    @WechatAuthorize
    public ResultData deleteById(@RequestBody BusVisitRegister condition) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "删除成功！");
        try {
            busVisitRegisterService.deleteById(condition.getId());
        } catch (Exception e) {
            resultData.setResultCode(Const.FAILED_CODE);
            resultData.setMsg("删除失败！");
        }
        return resultData;
    }

    /**
     * 维修数据导出
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export_repair(String time) {
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String startDateTime = null, endDateTime = null;
            if (StringUtils.isNotEmpty(time)) {
                String[] seTime = time.split(" - ");
                if (seTime.length > 1) {
                    startDateTime = seTime[0].trim();
                    endDateTime = seTime[1].trim();
                }
            }
            List<BusVisitRegister> list = busVisitRegisterService.getList(startDateTime, endDateTime);
            if (list != null) {
                LinkedHashMap<String, String> fmap = new LinkedHashMap<>();
                fmap.put("_index", "序号");
                fmap.put("visittimeStr", "来访时间");
                fmap.put("createtime", "登记时间");
                fmap.put("realEstateName", "楼盘名称");
                fmap.put("customer", "客户姓名");
                fmap.put("phone", "联系方式");
                fmap.put("_team_adviser", "所属团队");
                fmap.put("adviser", "置业顾问");
                fmap.put("address", "居住区域");
                fmap.put("occupation", "职业");
                fmap.put("times", "到访次数（首访、二访、三访）");
                fmap.put("purpose", "购买用途（刚需、婚房、改善、投资）");
                fmap.put("payment", "付款方式（商贷、公积金、全款）");
                fmap.put("level", "意向等级（A、B、C）");
                fmap.put("productType", "意向/成交户型及面积（高层、洋房）");
                fmap.put("area", "意向面积");
                fmap.put("intention", "购买意向");
                fmap.put("nodeal", "未成交原因（短语简单描述）");
                fmap.put("property", "报备人属性");
                fmap.put("reporter", "报备人姓名");
                fmap.put("remark", "备注");

                HSSFWorkbook book = ExcelUtil.createWorkBook("安策营销案场来访登记表", list, fmap);
                exportExcel("安策营销案场来访登记表", book);
            }
        } catch (Exception e) {
            addLogError("维修数据列表导出失败", SysLog.LogType.其他, e);
        }
    }


    /**
     * Excel对象写入响应流下载
     *
     * @param fileName
     * @param book
     */
    protected void exportExcel(String fileName, HSSFWorkbook book) {
        String name = fileName;
        try {
            fileName = fileName + "-" + DateUtil.toDateString(new Date(), "yyyyMMddHHmmss") + ".xls";
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream os = response.getOutputStream();
            book.write(os);
            os.flush();
            os.close();
            addLogInfo("导出Excel:" + name, SysLog.LogType.其他);
        } catch (Exception e) {
            addLogError("导出Excel:" + name, SysLog.LogType.其他, e);
        }
    }
}
