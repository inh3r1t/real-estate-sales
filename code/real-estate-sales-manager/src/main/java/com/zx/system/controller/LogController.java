package com.zx.system.controller;

import com.zx.base.controller.BaseController;
import com.zx.base.enums.TypeEnums;
import com.zx.base.exception.BusinessException;
import com.zx.base.annotation.AuthorizeIgnore;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ReturnModel;
import com.zx.lib.utils.DateUtil;
import com.zx.system.model.SysLog;
import com.zx.system.model.SysModule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统日志接口实现
 *
 * @author Xiafl.
 * @version 2017/12/04
 */
@Controller
@RequestMapping("log")
public class LogController extends BaseController {
    /**
     * 模块主页
     **/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("LogTypes", SysLog.LogType.values());
        model.addAttribute("LogLevels", SysLog.LogLevel.values());
        model.addAttribute("SysModules", module2Tree());
        model.addAttribute("UserLevel", currentUserLogin().getIssuper());
        return "system/log/list";
    }

    /**
     * 获取数据
     **/
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(Integer pageSize, Integer page, String module, Integer logLevel,
                          Integer logType, String time, Boolean showAllUser) throws BusinessException {
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        if (page == null) {
            page = 1;
        }

        Date startDateTime = null, endDateTime = null;
        if (StringUtils.isNotEmpty(time)) {
            String[] seTime = time.split(" - ");
            if (seTime.length > 1) {
                Calendar startTime = DateUtil.getCalendar(seTime[0], "yyyy-MM-dd HH:mm:ss");
                Calendar endTime = DateUtil.getCalendar(seTime[1], "yyyy-MM-dd HH:mm:ss");
                if (startTime.compareTo(Calendar.getInstance()) <= 0 && startTime.compareTo(endTime) < 0) {
                    startDateTime = startTime.getTime();
                    endDateTime = endTime.getTime();
                }
            }
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderBy", "createtime desc");
        paramMap.put("loglevel", logLevel);
        paramMap.put("logtype", logType);
        paramMap.put("startDateTime", startDateTime);
        paramMap.put("endDateTime", endDateTime);
        paramMap.put("createrid", (showAllUser != null && showAllUser) ? "" : currentUserLogin().getUserid());
        paramMap.put("mcode", module);

        try {
            int count = logService.selectCount(paramMap);
            paramMap.put("start", (page - 1) * pageSize);
            paramMap.put("pageSize", pageSize);
            List<SysLog> logList = logService.getList(paramMap);

            List<SysModule> modules = moduleService.selectList();
            for (SysLog log : logList) {
                log.setLoglevelname(SysLog.LogLevel.getName(log.getLoglevel()));
                log.setLogtypename(SysLog.LogType.getName(log.getLogtype()));
                log.setMname(getMNameBy(modules, log.getMcode()));
            }
            return new PagerModel<>(pageSize, page, count, logList);
        } catch (Exception e) {
            throw new BusinessException("数据查询异常", e);
        }
    }

    /**
     * 根据模块编码获取模块名称
     **/
    private String getMNameBy(List<SysModule> modules, String mcode) {
        Optional<SysModule> optional = modules.stream().filter(m -> m.getMcode().equals(mcode)).findFirst();
        return !optional.isPresent() ? "" : optional.get().getMname();
    }

    private List<SysModule> module2Tree() {
        List<SysModule> allModules = moduleService.selectList();
        List<SysModule> root = allModules.stream()
                .filter(m -> (StringUtils.isEmpty(m.getParentcode()))
                        && m.getMtype().equals(TypeEnums.ModuleType.菜单.name()))
                .sorted(Comparator.comparingInt(SysModule::getSortnum))
                .collect(Collectors.toList());

        for (SysModule module : root) {
            initModuleChild(module, allModules);
        }

        List<SysModule> result = new ArrayList<>();
        for (int i = 0; i < root.size(); i++) {
            result.addAll(getChildModules(root.get(i)));
        }
        return result;
    }

    private List<SysModule> initModuleChild(SysModule parentModule, List<SysModule> all) {
        String parentCode = parentModule.getMcode();
        List<SysModule> childModules = all.stream()
                .filter(m -> m.getParentcode().equals(parentCode)
                        && m.getMtype().equals(TypeEnums.ModuleType.菜单.name()))
                .sorted(Comparator.comparingInt(SysModule::getSortnum))
                .collect(Collectors.toList());
        parentModule.setChildren(childModules);
        for (SysModule module : childModules) {
            module.setMname(parentModule.getMname() + " - " + module.getMname());
            initModuleChild(module, all);
        }
        return childModules;
    }

    private List<SysModule> getChildModules(SysModule module) {
        List<SysModule> result = new ArrayList<>();
        result.add(module);
        if (module.getChildren() == null) {
            return result;
        }
        for (SysModule child : module.getChildren()) {
            result.addAll(getChildModules(child));
        }
        return result;
    }

    /**
     * 清除系统日志
     * （开发管理员权限）
     **/
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public Object clear() {
        try{
            logService.deleteAll();
            return new ReturnModel(true, "清除成功");
        }catch (Exception e){
            addLogError("清楚日志失败", SysLog.LogType.删除,e);
            throw new BusinessException(e);
        }
    }

    /**
     * 详细页
     **/
    @AuthorizeIgnore
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Integer id, Model model) {
        SysLog log = logService.selectById(id);
        if (log != null) {
            List<SysModule> modules = moduleService.selectList();
            log.setLoglevelname(SysLog.LogLevel.getName(log.getLoglevel()));
            log.setLogtypename(SysLog.LogType.getName(log.getLogtype()));
            log.setMname(getMNameBy(modules, log.getMcode()));
        }
        model.addAttribute("LogItem", log);
        return "system/log/detail";
    }
}
