package com.zx.system.controller;

import com.zx.base.annotation.FormReSubmitValidation;
import com.zx.base.controller.BaseController;
import com.zx.base.model.ReturnModel;
import com.zx.base.model.TreeJsonEntity;
import com.zx.system.model.SysLog;
import com.zx.system.model.SysModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 模块管理业务逻辑类
 *
 * @author wanght
 * @version 2017-12-05 1.0.0
 */
@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController {

    private static final int CODE_MAX = 999;

    /**
     * 列表页
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "system/module/list";
    }

    /**
     * 菜单模块添加
     *
     * @param parentid 父级ID
     * @param parentcode 父级Code
     * @param parentname 父级名称
     * @param model model
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @FormReSubmitValidation
    @RequestMapping( value = "/add",method = RequestMethod.GET )
    public String add(Integer parentid, String parentcode, String parentname, Model model) throws UnsupportedEncodingException {
        SysModule sysModule = new SysModule();
        sysModule.setParentid(parentid);
        sysModule.setParentcode(parentcode);
        sysModule.setParentname(URLDecoder.decode(parentname, "UTF-8"));
        model.addAttribute("sysModule", sysModule);
        return "system/module/edit";
    }

    /**
     * 菜单模块编辑
     *
     * @param id
     * @param model
     * @return
     */
    @FormReSubmitValidation
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, Model model) {
        SysModule sysModule = new SysModule();
        if (id != null) {
            sysModule = moduleService.selectById(id);
            if (!sysModule.getParentcode().isEmpty()) {
                sysModule.setParentname(moduleService.selectByCode(sysModule.getParentcode()).getMname());
            }
        }
        model.addAttribute("sysModule", sysModule);
        return "system/module/edit";
    }

    /**
     * 菜单新增/编辑提交
     *
     * @param sysModule
     *
     * @param parentcode 父级编码
     * @return
     */
    @FormReSubmitValidation(true)
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Object submit(@Valid SysModule sysModule, String parentcode) {
        ReturnModel rm = new ReturnModel();
        boolean isNew = (sysModule.getId() == null) || (sysModule.getId() == 0);
        try {
            if (sysModule.getId() == null) {
                Map.Entry entry = generateModuleSubCode(parentcode);
                if ((entry != null) && (boolean) entry.getKey()) {
                    sysModule.setMcode(entry.getValue().toString());
                    sysModule.setState(0);
                    sysModule.setCreatetime(new Date());
                } else {
                    rm.setState(false);
                    rm.setMessage(entry.getValue().toString());
                    return rm;
                }
            }
            moduleService.update(sysModule);

            if (isNew) {
                addLogInfo("《" + sysModule.getMname() + "》模块新增成功", SysLog.LogType.新增);
                rm.setMessage("新增成功");
            } else {
                addLogInfo("《" + sysModule.getMname() + "》模块修改成功", SysLog.LogType.修改);
                rm.setMessage("修改成功");
            }
            rm.setState(true);
        } catch (Exception ex) {
            rm.setState(false);
            rm.setMessage("系统异常，稍后再试");
        }

        return rm;
    }

    private Map.Entry generateModuleSubCode(String parentCode) {
        HashMap<Boolean,String> map = new HashMap<Boolean,String>();
        List<SysModule> subList = moduleService.getSubModules(parentCode);
        Integer currentMax = 0;
        List<Integer> subCodeList = new ArrayList<>();
        if (subList.size() > 0) {
            for (SysModule sysModule : subList) {
                subCodeList.add(Integer.parseInt(sysModule.getMcode()
                        .substring(sysModule.getMcode().length() - 3,
                                sysModule.getMcode().length())));
            }

            subCodeList.sort(Comparator.reverseOrder());
            currentMax = subCodeList.get(0);
        }
        if (currentMax >= CODE_MAX) {
            map.put(false, "菜单数量已到上限，不可以继续创建");
        } else {
            map.put(true, parentCode + String.format("%03d", currentMax + 1));
        }
        java.util.Iterator it = map.entrySet().iterator();
        if (it.hasNext()) {
            return (java.util.Map.Entry) it.next();
        }
        return null;
    }

    /**
     * 删除模块
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer id) {
        ReturnModel msg = new ReturnModel();

        if (id != null) {
            try {
                SysModule sysModule = moduleService.selectById(id);

                sysModule.setState(-1);
                moduleService.update(sysModule);
                moduleService.deleteListByCode(sysModule.getMcode());
                msg.setState(true);
                msg.setMessage(sysModule.getMname() + "删除成功");
                addLogInfo("《" + sysModule.getMname() + "》模块删除成功", SysLog.LogType.删除);
            } catch (Exception ex) {
                msg.setState(false);
                msg.setMessage("系统异常，稍后再试");
            }
        } else {
            msg.setState(false);
            msg.setMessage("没有找到删除的对象");
        }

        return msg;
    }

    /**
     * 获取部门树形展示的Json数据
     *
     * @param code
     * @return
     */
    @RequestMapping("treeJson")
    @ResponseBody
    public Object treeJson(String code) {
        String parentCode = (code != null)
                ? code
                : "";
        List<SysModule> list = moduleService.selectList();
        List<TreeJsonEntity> treeList = modules2TreeJsonEntity(list.stream()
                        .filter(l -> l.getParentcode().equals(parentCode))
                        .collect(Collectors.toList()),
                list);

        return treeList;
    }

    /**
     * 验证模块名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/moduleIsExisted")
    public Object moduleIsExisted(String name, String id, String mcode) {
        String code = "";
        SysModule result = moduleService.moduleIsExisted(name, id, mcode, code);
        ReturnModel rm = new ReturnModel();
        rm.setState(result != null);
        rm.setMessage((result != null)
                ? "名称重复"
                : "名称不重复");

        return rm;
    }
}
