package com.zx.system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zx.base.annotation.FormReSubmitValidation;
import com.zx.base.controller.BaseController;
import com.zx.base.enums.TypeEnums;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ReturnModel;
import com.zx.lib.utils.encrypt.Md5Util;
import com.zx.system.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.util.*;

/**
 * 角色管理
 *
 * @author wanght
 * @version 2017-12-05 1.0.0
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final String MENUINFO = "角色";

    /**
     * 删除
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
                SysRole role = roleService.selectById(id.intValue());
                List<SysUser> list = new ArrayList<>();
                if (list.size() > 0) {
                    msg.setState(false);
                    msg.setMessage("该角色下存在有效的用户，请先删除用户");
                } else {
                    role.setState(-1);
                    roleService.update(role);
                    msg.setState(true);
                    msg.setMessage(logFormat(SysLog.LogType.删除, MENUINFO, role.getName(), TypeEnums.IsSuccess.成功));
                    addLogInfo(logFormat(SysLog.LogType.删除, MENUINFO, role.getName(), TypeEnums.IsSuccess.成功), SysLog.LogType.删除);
                }
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
     * Method description
     *
     * @param model
     * @param id
     * @return
     */
    @FormReSubmitValidation
    @RequestMapping(value = "/edit")
    public String edit(Model model, Integer id) {
        SysRole role = new SysRole();

        if (id != null) {
            role = roleService.selectById(id.intValue());
        }
        model.addAttribute("sysRole", role);
        model.addAttribute("roleType", TypeEnums.RoleType.toList());
        return "system/role/edit";
    }

    /**
     * Method description
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public String list() {
        return "system/role/list";
    }

    /**
     * Method description
     *
     * @param pageSize
     * @param page
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(Integer pageSize, Integer page, String name, String roleType) throws JsonProcessingException {
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        if (page == null) {
            page = 1;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("state", 0);
        if (!StringUtils.isEmpty(roleType)) {
            paramMap.put("roletype", roleType);
        }

        if (!StringUtils.isEmpty(name)) {
            paramMap.put("nameFuzzy", name);
        }

        paramMap.put("orderBy", "createtime desc");
        // 隐藏超管角色
        paramMap.put("isadmin", false);
        int userCount = roleService.selectCount(paramMap);
        paramMap.put("start", (page - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        List<SysRole> userList = roleService.getList(paramMap);
        return new PagerModel<>(pageSize, page, userCount, userList);
    }

    /**
     * Method description
     *
     * @param role
     * @return
     * @throws JsonProcessingException
     */
    @FormReSubmitValidation(true)
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Object submit(@Valid SysRole role) throws JsonProcessingException {
        ReturnModel returnModel = new ReturnModel();
        try {
            // 表单后台验证
            if (StringUtils.isEmpty(role.getName())) {
                returnModel.setState(false);
                returnModel.setMessage("角色名不能为空。");
                return returnModel;
            } else if (roleService.roleIsExisted(role.getName(), role.getId())) {
                returnModel.setState(false);
                returnModel.setMessage("已存在相同的角色名称。");
                return returnModel;
            }
            boolean isAdd = false;
            //是否为新增角色
            if (role.getId() == null || role.getId() == 0) {
                isAdd = true;

            }
            role = roleService.update(role);
            if (isAdd) {
                // 新增
                returnModel.setMessage("《" + role.getName() + "》角色新增成功");
                addLogInfo("《" + role.getName() + "》角色新增成功", SysLog.LogType.新增);
            } else {
                // 修改
                addLogInfo("《" + role.getName() + "》角色修改成功", SysLog.LogType.修改);
                returnModel.setMessage("《" + role.getName() + "》角色修改成功");
            }
            returnModel.setState(true);

        } catch (Exception e) {
            returnModel.setState(false);
            returnModel.setMessage("操作失败，请稍后再试。");
        }
        return returnModel;
    }

    /**
     * 查询权限列表
     *
     * @return
     * @author
     */
    @ResponseBody
    @RequestMapping("/getpowermodulelist")
    public Object getPowerModuleList(Integer roleId) {
        ReturnModel rm = new ReturnModel();
        try {
            List<SysModule> roleModules = moduleService.selectModulesByRoleId(roleId);
            List<SysModule> allSysModules = moduleService.selectList();
            for (SysModule smodule : allSysModules) {
                for (SysModule rmodule : roleModules) {
                    if (rmodule.getId().equals(smodule.getId())) {
                        smodule.setChecked(true);
                    }
                }
            }
            rm.setState(true);
            rm.setModel(allSysModules);
        } catch (Exception e) {
            rm.setState(false);
        }
        return rm;
    }

    @RequestMapping("/power_module")
    public String powerModule(Model model, Integer id, String rolename) {
        model.addAttribute("roleid", id);
        model.addAttribute("rolename", rolename);
        return "system/role/power_module";
    }

    @RequestMapping(value = "/submitPowerModule")
    @ResponseBody
    public Object submitPowerModule(Integer roleid, @RequestParam("selectmids[]") String[] selectmids) throws JsonProcessingException {
        ReturnModel rm = new ReturnModel();
        try {
            SysRole role = roleService.selectById(roleid);
            List<SysRolemodule> rmList = new ArrayList<>();
            for (String item : selectmids) {
                SysRolemodule model = new SysRolemodule();
                model.setRoleid(roleid);
                model.setModuleid(Integer.parseInt(item));
                model.setCreatetime(new Date());
                rmList.add(model);
            }
            roleService.updateRoleModules(roleid, rmList);
            rm.setState(true);
            rm.setMessage(logFormat(SysLog.LogType.配置, MENUINFO, role.getName(), TypeEnums.IsSuccess.成功));
            addLogInfo(logFormat(SysLog.LogType.配置, MENUINFO, role.getName(), TypeEnums.IsSuccess.成功), SysLog.LogType.配置);
        } catch (Exception e) {
            rm.setState(false);
            rm.setMessage("配置角色模板失败");
        }
        return rm;
    }

    /**
     * Method description
     *
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/getAllRole", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllRole() throws JsonProcessingException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("state", 0);
        paramMap.put("orderBy", "createtime desc");
        List<SysRole> userList = roleService.getList(paramMap);
        return userList;
    }

    /**
     * 验证是否存在相同角色名
     *
     * @param name 角色名
     * @param id   用户ID
     * @return Object 如果不存在返回true,否则返回false
     */
    @RequestMapping(value = "/roleIsExisted", method = RequestMethod.GET)
    @ResponseBody
    public Object roleIsExisted(String name, Integer id) {
        if (StringUtils.isEmpty(name)) {
            return true;
        }
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("name", name);
        List<SysRole> sysRoles = roleService.getList(queryMap);
        return !sysRoles.stream().anyMatch(p -> !p.getId().equals(id));
    }

}
