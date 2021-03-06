package com.zx.business.controller;

import com.zx.base.annotation.WechatAuthorize;
import com.zx.base.common.Const;
import com.zx.base.common.ExcelUtil;
import com.zx.base.enums.TypeEnums;
import com.zx.base.exception.WechatAuthException;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ResultData;
import com.zx.business.model.BusRealEstate;
import com.zx.business.model.BusVisitRegister;
import com.zx.business.service.BusRealEstateService;
import com.zx.business.service.BusVisitRegisterService;
import com.zx.business.vo.VisitRegisterVO;
import com.zx.lib.utils.DateUtil;
import com.zx.lib.utils.StringUtil;
import com.zx.system.model.SysLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Resource
    private BusRealEstateService busRealEstateService;

    @RequestMapping("/list")
    public String list(Model model) {
        List<BusRealEstate> list = busRealEstateService.getList(new BusRealEstate());
        List<HashMap<String, Object>> selects = new ArrayList<>();

        for (BusRealEstate item : list) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("text", item.getName());
            hashMap.put("value", item.getId());
            selects.add(hashMap);
        }
        model.addAttribute("realEtates", selects);
        return "business/busVisitRegister/list";
    }


    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    @ResponseBody
    @WechatAuthorize
    public ResultData getPage(@RequestBody VisitRegisterVO model) {
        ResultData resultData = new ResultData(Const.SUCCESS_CODE, "获取列表成功！");
        try {
            wechatAuthCheck(request);
            String token = request.getHeader("token");
            if (model.getPage() == null) {
                model.setPage(1);
            }
            if (model.getPageSize() == null) {
                model.setPageSize(PAGE_SIZE);
            }

            Map<String, Object> paramMap = new HashMap<>();

            if (StringUtil.isNotEmpty(model.getStartDate())) {
                paramMap.put("startVisitTime", model.getStartDate() + " 00:00:00");
            }
            if (StringUtil.isNotEmpty(model.getEndDate())) {
                paramMap.put("endVisitTime", model.getEndDate() + " 23:59:59");
            }
            if (model.getRealEstateId() != null && model.getRealEstateId() > 0) {
                paramMap.put("real_estate_id", model.getRealEstateId());
            }

            paramMap.put("orderField", "createtime");
            paramMap.put("orderType", "desc");
            paramMap.put("createrid", getUserByToken(token).getId());
            if (StringUtil.isNotEmpty(model.getKeyword())) {
                paramMap.put("keywordFuzzy", model.getKeyword());
            }
            int count = busVisitRegisterService.selectCount(paramMap);

            paramMap.put("start", (model.getPage() - 1) * model.getPageSize());
            paramMap.put("pageSize", model.getPageSize());
            List<BusVisitRegister> busRealEstate =
                    busVisitRegisterService.getPage(paramMap);
            PagerModel<BusVisitRegister> busRealEstatePage = new PagerModel<>(model.getPageSize(), model.getPage(), count, busRealEstate);
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
    public Object getList(BusVisitRegister model,
                          String time,
                          String team,
                          String level,
                          String intention,
                          String property,
                          String times,
                          Integer real_estate_id) {
        try {
            if (model.getPage() == null) {
                model.setPage(1);
            }
            if (model.getPageSize() == null) {
                model.setPageSize(PAGE_SIZE);
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();
            String startDateTime = null, endDateTime = null;
            if (StringUtils.isNotEmpty(time)) {
                String[] seTime = time.split(" - ");
                if (seTime.length > 1) {
                    startDateTime = seTime[0].trim();
                    endDateTime = seTime[1].trim();

                    paramMap.put("startDateTime", startDateTime);
                    paramMap.put("endDateTime", endDateTime);
                }
            }

            paramMap.put("orderField", "createtime");
            paramMap.put("orderType", "desc");
            if (StringUtil.isNotEmpty(team)) {
                paramMap.put("team", team);
            }
            if (StringUtil.isNotEmpty(level)) {
                paramMap.put("level", level);
            }
            if (StringUtil.isNotEmpty(intention)) {
                paramMap.put("intention", intention);
            }
            if (StringUtil.isNotEmpty(property)) {
                paramMap.put("property", property);
            }
            if (StringUtil.isNotEmpty(times)) {
                paramMap.put("times", times);
            }
            if (real_estate_id != null && real_estate_id > 0) {
                paramMap.put("real_estate_id", real_estate_id);
            }
            int count = busVisitRegisterService.selectCount(paramMap);

            paramMap.put("start", (model.getPage() - 1) * model.getPageSize());
            paramMap.put("pageSize", model.getPageSize());
            List<BusVisitRegister> busRealEstate =
                    busVisitRegisterService.getPage(paramMap);
            return new PagerModel<>(model.getPageSize(), model.getPage(), count, busRealEstate);


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
                model.setCreatetime(DateUtil.getDateNow());
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
    public void export(
            String time,
            String team,
            String level,
            String intention,
            String property,
            String times) {
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String startDateTime = null, endDateTime = null;
            if (StringUtils.isNotEmpty(time)) {
                String[] seTime = time.split(" - ");
                if (seTime.length > 1) {
                    startDateTime = seTime[0].trim();
                    endDateTime = seTime[1].trim();

                    paramMap.put("startDateTime", startDateTime);
                    paramMap.put("endDateTime", endDateTime);
                }
            }

            paramMap.put("orderField", "createtime");
            paramMap.put("orderType", "desc");
            if (StringUtil.isNotEmpty(team)) {
                paramMap.put("team", team);
            }
            if (StringUtil.isNotEmpty(level)) {
                paramMap.put("level", level);
            }
            if (StringUtil.isNotEmpty(intention)) {
                paramMap.put("intention", intention);
            }
            if (StringUtil.isNotEmpty(property)) {
                paramMap.put("property", property);
            }
            if (StringUtil.isNotEmpty(times)) {
                paramMap.put("times", times);
            }

            List<BusVisitRegister> list =
                    busVisitRegisterService.getPage(paramMap);
            if (list != null) {
                LinkedHashMap<String, String> fmap = new LinkedHashMap<>();
                fmap.put("_index", "序号");
                fmap.put("visittimeStr", "来访时间");
                fmap.put("createtime", "登记时间");
                fmap.put("realEstateName", "楼盘名称");
                fmap.put("customer", "客户姓名");
                fmap.put("phone", "联系方式");
                fmap.put("team", "所属团队");
                fmap.put("adviser", "置业顾问");
                fmap.put("address", "居住区域");
                fmap.put("occupation", "职业");
                fmap.put("times", "到访次数");
                fmap.put("purpose", "购买用途");
                fmap.put("payment", "付款方式");
                fmap.put("level", "意向等级");
                fmap.put("productType", "意向/成交户型及面积");
                fmap.put("area", "意向面积");
                fmap.put("intention", "购买意向");
                fmap.put("nodeal", "未成交原因");
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
