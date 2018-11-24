package com.zx.system.controller;

import com.zx.base.annotation.FormReSubmitValidation;
import com.zx.base.controller.BaseController;
import com.zx.base.enums.TypeEnums;
import com.zx.base.model.ReturnModel;
import com.zx.base.model.TreeJsonEntity;
import com.zx.system.model.SysDepartment;
import com.zx.system.model.SysLog;
import com.zx.system.model.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门控制
 *
 * @author V.E.
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {
    private static final String MENUINFO = "部门";

    /**
     * 部门列表数据
     */
    @RequestMapping("/list")
    public String list() {
        return "system/dept/list";
    }

    /**
     * 编辑部门页面
     *
     * @return
     */
    @FormReSubmitValidation
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, String parentCode, Model model) {
        SysDepartment sysDepartment = new SysDepartment();
        if (id != null) {
            sysDepartment = deptService.selectById(id);
            if (parentCode == null || parentCode.isEmpty()) {
                parentCode = sysDepartment.getDcode().substring(0, sysDepartment.getDcode().length() - 3);
            }
        }
        List<SysDepartment> list = getDepartmentListIsOffSet("");
        model.addAttribute("sysDepartment", sysDepartment);
        model.addAttribute("parentCode", parentCode);
        model.addAttribute("list", list);
        if (parentCode == null || parentCode.isEmpty()) {
            model.addAttribute("parentName", "");
        } else {
            SysDepartment pDept = deptService.selectByCode(parentCode);
            model.addAttribute("parentName", pDept != null ? pDept.getDname() : "");
        }
        return "system/dept/edit";
    }

    @FormReSubmitValidation(true)
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Object submit(@Valid SysDepartment sysDepartment, String parentCode) {
        ReturnModel rm = new ReturnModel();
        boolean isNew = sysDepartment.getId() == null || sysDepartment.getId() == 0;
        try {
            if (deptIsExisted(sysDepartment.getDname(), sysDepartment.getId() == null ? "0" : sysDepartment.getId().toString())) {
                if (sysDepartment.getId() == null) {
                    Map.Entry entry = generateDeptSubCode(parentCode);
                    if (entry != null && (boolean) entry.getKey()) {
                        sysDepartment.setDcode(entry.getValue().toString());
                        sysDepartment.setState(0);
                        sysDepartment.setCreaterid(currentUserLogin().getUserid());
                        sysDepartment.setCreatetime(new Date());
                    } else {
                        rm.setState(false);
                        rm.setMessage(entry != null ? entry.getValue().toString() : "部门创建异常");
                        return rm;
                    }
                }
                deptService.update(sysDepartment);
                rm.setState(true);
                SysLog.LogType logType = isNew ? SysLog.LogType.新增 : SysLog.LogType.修改;
                addLogInfo(logFormat(logType, MENUINFO, sysDepartment.getDname(), TypeEnums.IsSuccess.成功), logType);
                rm.setMessage(logFormat(logType, MENUINFO, sysDepartment.getDname(), TypeEnums.IsSuccess.成功));
            } else {
                rm.setState(false);
                rm.setMessage("部门名称已存在");
            }
        } catch (Exception ex) {
            rm.setState(false);
            rm.setMessage("保存失败");
        }
        return rm;
    }

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
                SysDepartment sysDepartment = deptService.selectById(id);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("branchcodeFuzzy", sysDepartment.getDcode());
                int count = userService.selectCount(map);
                if (count > 0) {
                    msg.setState(false);
                    msg.setMessage("该部门或子级部门下存在有效的用户，请先删除用户");
                } else {
                    deptService.deleteListByCode(sysDepartment.getDcode());
                    msg.setState(true);
                    msg.setMessage(logFormat(SysLog.LogType.删除, MENUINFO, sysDepartment.getDname(), TypeEnums.IsSuccess.成功));
                    addLogInfo(logFormat(SysLog.LogType.删除, MENUINFO, sysDepartment.getDname(), TypeEnums.IsSuccess.成功), SysLog.LogType.删除);
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
     * 获取部门树形展示的Json数据
     */
    @RequestMapping("/treeJson")
    @ResponseBody
    public Object treeJson() {
        String code = currentUserLogin().getBranchcode();
        List<SysDepartment> list;
        List<TreeJsonEntity> treeList;
        try {
            if (currentUserLogin().getIssuper() != null && currentUserLogin().getIssuper()) {
                list = getDepartmentList("");
                treeList = depts2TreeJsonEntity(list.stream().filter(__ -> __.getDcode().length() == 3)
                        .collect(Collectors.toList()), list);
            } else {
                list = getDepartmentList(code);
                treeList = depts2TreeJsonEntity(list.stream().filter(__ -> __.getDcode().equals(code))
                        .collect(Collectors.toList()), list);
            }
        } catch (Exception ex) {
            treeList = new ArrayList<>();
            addLogError("获取部门列表异常", SysLog.LogType.其他, ex);
        }
        return treeList;

    }

    /**
     * 部门对象转成树形对象
     *
     * @param nodes
     * @param all
     * @return
     */
    public List<TreeJsonEntity> depts2TreeJsonEntity(List<SysDepartment> nodes, List<SysDepartment> all) {
        List<TreeJsonEntity> returnList = new ArrayList<>();
        for (SysDepartment item : nodes) {
            TreeJsonEntity entity = new TreeJsonEntity();
            entity.id = item.getId();
            entity.code = item.getDcode();
            entity.text = item.getDname();
            entity.value = item.getId().toString();
            entity.complete = true;
            entity.hasChildren = deptService.getSubDepartments(all, item.getDcode()).size() > 0;
            entity.childNodes = depts2TreeJsonEntity(deptService.getSubDepartments(all, item.getDcode()), all);
            entity.img = "home";
            entity.extra = new ExtraObject(item.getCreatetime(), item.getState(), item.getRemark(), item.getDisplayorder());
            returnList.add(entity);
        }
        return returnList;
    }

    class ExtraObject {
        public ExtraObject(java.util.Date date, Integer state, String remark, Integer displayOrder) {
            this.createtime = date;
            this.state = state;
            this.remark = remark;
            this.displayOrder = displayOrder;
        }

        public Date createtime;
        public Integer state;
        public String remark;
        public Integer displayOrder;
    }


    /**
     * 判断部门名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    public boolean deptIsExisted(String name, String id) {
        SysDepartment result = deptService.deptIsExisted(name, id);
        return result == null;
    }
}
