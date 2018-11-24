package com.zx.system.controller;

import com.zx.base.annotation.FormReSubmitValidation;
import com.zx.base.common.ValidateUtil;
import com.zx.base.controller.BaseController;
import com.zx.base.enums.TypeEnums;
import com.zx.base.exception.BusinessException;
import com.zx.base.model.ReturnModel;
import com.zx.base.model.TreeJsonEntity;
import com.zx.system.model.SysCategory;
import com.zx.system.model.SysLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 分类控制
 *
 * @author V.E.
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {
    private static final String MENUINFO = "分类";

    /**
     * 分类列表数据
     *
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("CategoryType", TypeEnums.CategoryType.values());
        return "system/category/list";
    }

    /**
     * 编辑分类页面
     */
    @FormReSubmitValidation
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, String parentCode, Model model, TypeEnums.CategoryType categoryType) {
        SysCategory sysCategory = new SysCategory();
        if (id != null) {
            sysCategory = sysCategoryService.selectById(id);
            if (parentCode == null || parentCode.isEmpty()) {
                parentCode = sysCategory.getCode().substring(0, sysCategory.getCode().length() - 3);
            }
        }
        List<SysCategory> list = getCategoryListIsOffSet("", categoryType);
        model.addAttribute("categoryType", categoryType);
        model.addAttribute("sysCategory", sysCategory);
        model.addAttribute("parentCode", parentCode);
        model.addAttribute("list", list);
        if (parentCode == null || parentCode.isEmpty()) {
            model.addAttribute("parentName", "");
        } else {
            SysCategory pCategory = sysCategoryService.selectByCode(parentCode);
            model.addAttribute("parentName", pCategory != null ? pCategory.getName() : "");
        }
        return "system/category/edit";
    }

    /**
     * 分类新增，修改
     *
     * @param sysCategory
     * @param parentCode
     * @param categoryType
     * @return
     */
    @FormReSubmitValidation(true)
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Object submit(@Valid SysCategory sysCategory, String parentCode, TypeEnums.CategoryType categoryType) {
        ReturnModel rm = new ReturnModel();
        boolean isNew = sysCategory.getId() == null || sysCategory.getId() == 0;
        try {
            // 对表单进行验证
            if (!ValidateUtil.charString(sysCategory.getName())) {
                rm.setState(false);
                rm.setMessage(ValidateUtil.V_ERRORMESSAGE);
                return rm;
            }
            if (sysCategory.getId() == null || deptIsExisted(sysCategory.getName(), sysCategory.getId().toString())) {
                if (sysCategory.getId() == null) {
                    Map.Entry entry = generateCategorySubCode(parentCode);
                    if (entry != null && (boolean) entry.getKey()) {
                        sysCategory.setCode(entry.getValue().toString());
                        sysCategory.setState(TypeEnums.State.正常.getValue());
                        sysCategory.setCreaterid(currentUserLogin().getUserid());
                        sysCategory.setCreatetime(new Date());
                    } else {
                        rm.setState(false);
                        rm.setMessage(entry.getValue().toString());
                        return rm;
                    }
                }
                sysCategory.setType(categoryType.getValue());
                sysCategoryService.update(sysCategory);
                rm.setState(true);
                SysLog.LogType logType = isNew ? SysLog.LogType.新增 : SysLog.LogType.修改;
                addLogInfo(logFormat(logType, MENUINFO, sysCategory.getName(), TypeEnums.IsSuccess.成功), logType);
                rm.setMessage(logFormat(logType, MENUINFO, sysCategory.getName(), TypeEnums.IsSuccess.成功));
            } else {
                rm.setState(false);
                rm.setMessage("分类名称已存在");
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
                SysCategory sysCategory = sysCategoryService.selectById(id);
                sysCategoryService.deleteListByCode(sysCategory.getCode());
                msg.setState(true);
                msg.setMessage(logFormat(SysLog.LogType.删除, MENUINFO, sysCategory.getName(), TypeEnums.IsSuccess.成功));
                addLogInfo(logFormat(SysLog.LogType.删除, MENUINFO, sysCategory.getName(), TypeEnums.IsSuccess.成功), SysLog.LogType.删除);
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
     * 获取分类树形展示的Json数据
     */
    @RequestMapping("/treeJson")
    @ResponseBody
    public Object treeJson(TypeEnums.CategoryType categoryType) {
        String code = currentUserLogin().getBranchcode();
        List<SysCategory> list;
        if (categoryType == null) {
            categoryType = TypeEnums.CategoryType.默认分类;
        }
        List<TreeJsonEntity> treeList;
        try {
            if (currentUserLogin().getIssuper() != null && currentUserLogin().getIssuper()) {
                list = sysCategoryService.getSubsetsBranchByCode("", categoryType.getValue());
                treeList = depts2TreeJsonEntity(list.stream().filter(__ -> __.getCode().length() == 3)
                        .collect(Collectors.toList()), list);
            } else {
                list = sysCategoryService.getSubsetsBranchByCode(code, categoryType.getValue());
                treeList = depts2TreeJsonEntity(list.stream().filter(__ -> __.getCode().equals(code))
                        .collect(Collectors.toList()), list);
            }
        } catch (Exception ex) {
            treeList = new ArrayList<>();
            addLogError("获取分类列表异常", SysLog.LogType.其他, ex);
        }
        return treeList;

    }

    /**
     * 分类对象转成树形对象
     *
     * @param nodes
     * @param all
     * @return
     */
    public List<TreeJsonEntity> depts2TreeJsonEntity(List<SysCategory> nodes, List<SysCategory> all) {
        List<TreeJsonEntity> returnList = new ArrayList<>();
        for (SysCategory item : nodes) {
            TreeJsonEntity entity = new TreeJsonEntity();
            entity.id = item.getId();
            entity.code = item.getCode();
            entity.text = item.getName();
            entity.value = item.getId().toString();
            entity.complete = true;
            entity.hasChildren = getSubCategories(all, item.getCode()).size() > 0;
            entity.childNodes = depts2TreeJsonEntity(getSubCategories(all, item.getCode()), all);
            entity.img = "";
            entity.extra = new ExtraObject(item.getCreatetime(), item.getState(), item.getRemark());
            returnList.add(entity);
        }
        return returnList;
    }

    class ExtraObject {
        ExtraObject(Date date, Integer state, String remark) {
            this.createtime = date;
            this.state = state;
            this.remark = remark;
        }

        public Date createtime;
        public Integer state;
        public String remark;
    }


    /**
     * 判断分类名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    public boolean deptIsExisted(String name, String id) {
        SysCategory result = sysCategoryService.deptIsExisted(name, id);
        return result == null;
    }

    /**
     * 根据当前分类code生成下级分类code
     *
     * @param code
     * @return
     */
    public Map.Entry<Boolean, String> generateCategorySubCode(String code) {
        Map<Boolean, String> map = new HashMap<Boolean, String>();
        List<SysCategory> subList = sysCategoryService.getSubCategories(code);
        Integer currentMax = 0;
        List<Integer> subCodeList = new ArrayList<>();

        if (subList.size() > 0) {
            for (SysCategory sysCategory : subList) {
                subCodeList.add(Integer.parseInt(sysCategory.getCode()
                        .substring(sysCategory.getCode().length() - 3,
                                sysCategory.getCode().length())));
            }

            subCodeList.sort(Comparator.reverseOrder());
            currentMax = subCodeList.get(0);
        }

        if (currentMax >= 999) {
            map.put(false, "分类数量已到上限，不可以继续创建");
        } else {
            map.put(true, code + String.format("%03d", currentMax + 1));
        }

        java.util.Iterator it = map.entrySet().iterator();

        if (it.hasNext()) {
            return (java.util.Map.Entry) it.next();
        }

        return null;
    }

    /**
     * 获取子级节点
     *
     * @param all
     * @param categorycode
     * @return
     */
    private List<SysCategory> getSubCategories(List<SysCategory> all, String categorycode) {
        return all.stream().filter(__ -> __.getCode().startsWith(categorycode)
                && __.getCode().length() == categorycode.length() + 3)
                .collect(Collectors.toList());
    }
}
