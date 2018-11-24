package com.zx.system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zx.base.annotation.FormReSubmitValidation;
import com.zx.base.controller.BaseController;
import com.zx.base.enums.TypeEnums;
import com.zx.base.model.PagerModel;
import com.zx.base.model.ReturnModel;
import com.zx.lib.utils.DateUtil;
import com.zx.lib.utils.StringUtil;
import com.zx.lib.utils.encrypt.Md5Util;
import com.zx.system.model.*;
import com.zx.system.service.RoleService;
import com.zx.system.service.UserRoleService;
import com.zx.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.sl.draw.geom.Guide;
import org.apache.tomcat.jni.Directory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 用户管理业务逻辑类
 *
 * @author wangx
 * @version 2017-12-04 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 用户列表
     */
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("LoginId", currentUserLogin().getUserid());
        model.addAttribute("StatusType", TypeEnums.StatusType.values());
        return "system/user/list";
    }

    /**
     * 获取用户列表数据
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(Integer pageSize, Integer page, String username, String fullname,
                          @RequestParam(value = "state", required = false) Integer state) throws JsonProcessingException {
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        if (page == null) {
            page = 1;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderBy", "createtime desc");
        paramMap.put("usernameFuzzy", username);
        paramMap.put("fullnameFuzzy", fullname);
        paramMap.put("state", state);
        // 排除掉超管角色用户
        Map<String, Object> roleMap = new HashMap<String, Object>();
        roleMap.put("isadmin", true);
        List<SysRole> sysRoles = roleService.getList(roleMap);
        if (sysRoles.size() > 0) {
            List<Integer> superRoleIds = new ArrayList<>();
            for (SysRole sysRole : sysRoles) {
                superRoleIds.add(sysRole.getId());
            }
            paramMap.put("notInRole", superRoleIds);
        }

        int userCount = userService.selectCount(paramMap);
        paramMap.put("pageSize", pageSize);
        paramMap.put("start", (page - 1) * pageSize);
        List<SysUser> userList = userService.getList(paramMap);
        return new PagerModel<>(pageSize, page, userCount, userList);
    }

    /**
     * 修改密码
     */
    @FormReSubmitValidation
    @RequestMapping("/password")
    public String password(Model model) {
        model.addAttribute("user", userService.getByID(currentUserLogin().getUserid()));
        return "system/user/password";
    }

    /**
     * 个人信息
     */
    @FormReSubmitValidation
    @RequestMapping("/personalData")
    public String personalData(Model model) {


        SysUser user = userService.getByID(currentUserLogin().getUserid());

        model.addAttribute("type", "edit");

        List<SysDepartment> deptList = getDepartmentListIsOffSet("");
        deptList = sortDept(deptList);
        if (user != null) {
            if (user.getPhoto() == null || user.getPhoto().length() == 0) {
                user.setPhoto("/images/avatar.png");
            }
        }
        List<SysRole> roleList = roleService.getList(new HashMap<String, Object>());
        model.addAttribute("userinfo", user);
        model.addAttribute("deptList", deptList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("sexList", TypeEnums.Sex.toList());
        return "system/user/personalData";

    }


    /**
     * 上传头像页面
     */
    @RequestMapping("/avatar")
    public String avatar(Model model) {
        SysUser user = userService.getByID(currentUserLogin().getUserid());
        if (user != null && user.getPhoto() != null && user.getPhoto().length() > 0) {
            model.addAttribute("photo", user.getPhoto());
        } else {
            //默认头像
            model.addAttribute("photo", "/images/avatar.png");
        }

        return "system/user/avatar";
    }

    /**
     * 上传头像方法
     */

    @RequestMapping("/uploadAvatar")
    @ResponseBody
    public Object uploadAvatar(@RequestParam("data") String data) throws IOException {
        ReturnModel rm = new ReturnModel();
        BASE64Decoder decoder = new BASE64Decoder();
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String relativePath = "/images/avatar/";
        String directory = (this.getClass().getClassLoader().getResource("").getPath()) + "static/";
        try {
            directory = java.net.URLDecoder.decode(directory, "utf-8").substring(1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String savePath = relativePath + uuid + ".jpg";
        String filePath = (directory + savePath);
        directory += relativePath;
        File dirFile = new File(directory);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        // Base64解码
        byte[] b;
        try {
            b = decoder.decodeBuffer(data.split(",")[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
            SysUser user = userService.getByID(currentUserLogin().getUserid());
            user.setPhoto(savePath);
            userService.save(user);
            currentUserLogin().setLoginphoto(savePath);
            rm.setInfo(true, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        rm.setInfo(false, "上传错误，请重试");
        return rm;
    }


    /**
     * 修改当前账号密码
     *
     * @param psd         原密码
     * @param newPassword 新密码
     */
    @FormReSubmitValidation(true)
    @RequestMapping("/changePassword")
    @ResponseBody
    public Object changePassword(String psd, String newPassword) {
        try {
            SysUser sysUser = userService.getByID(currentUserLogin().getUserid());
            if (!sysUser.getPsd().equals(Md5Util.getMd5(psd))) {
                return new ReturnModel(false, "原密码不正确");
            }
            sysUser.setPsd(Md5Util.getMd5(newPassword));
            userService.save(sysUser);
            addLogInfo(String.format("%s密码修改成功", sysUser.getUsername()), "", SysLog.LogType.修改);
            return new ReturnModel(true, "密码修改成功");
        } catch (Exception e) {
            addLogError("密码修改失败", "", SysLog.LogType.修改, e, currentUserLogin());
            return new ReturnModel(false, "密码修改失败");
        }
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/resetpwd")
    @ResponseBody
    public Object resetPassword(Integer id) {
        if (id == null || id <= 0) {
            return new ReturnModel(false, "密码重置失败，请重新操作");
        }
        try {
            SysUser sysUser = userService.getByID(id);
            if (sysUser == null || sysUser.getId() <= 0) {
                return new ReturnModel(false, "用户不存在");
            }
            sysUser.setPsd(Md5Util.getMd5("000000"));
            userService.save(sysUser);
            addLogInfo(String.format("对用户%s密码重置成功", sysUser.getUsername()), "", SysLog.LogType.修改);
            return new ReturnModel(true, "密码重置成功");
        } catch (Exception ex) {
            addLogError(String.format("对用户ID为%s的密码重置失败", id), "", SysLog.LogType.新增, ex);
            return new ReturnModel(false, "密码重置失败");
        }
    }

    /**
     * 编辑用户
     */
    @FormReSubmitValidation
    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id", required = false) Integer id) {
        SysUser user = new SysUser();
        if (id != null && !id.equals(0)) {
            user = userService.getByID(id);
            model.addAttribute("type", "edit");
        } else {
            model.addAttribute("type", "add");
        }
        List<SysDepartment> deptList = getDepartmentListIsOffSet("");
        deptList = sortDept(deptList);
        List<SysRole> roleList = roleService.getList(new HashMap<String, Object>());
        model.addAttribute("userinfo", user);
        model.addAttribute("deptList", deptList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("sexList", TypeEnums.Sex.toList());
        return "system/user/edit";
    }

    /**
     * 用户角色分配
     */
    @FormReSubmitValidation
    @RequestMapping("/role")
    public String role(Model model, @RequestParam(value = "id", required = false) Integer id) {
        SysUser sysUser = userService.getByID(id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("state", TypeEnums.State.正常.getValue());
        paramMap.put("isadmin", false);
        List<SysRole> roleList = roleService.getList(paramMap);
        model.addAttribute("roleList", roleList);
        model.addAttribute("userinfo", sysUser);
        return "system/user/role";
    }

    /**
     * 添加修改用户
     *
     * @param user 用户
     * @return
     */
    @FormReSubmitValidation(true)
    @RequestMapping(value = "/submit")
    @ResponseBody
    public Object submit(@Valid SysUser user, String deadTime) throws JsonProcessingException {
        boolean insertAction = user.getId() == null || user.getId() == 0;
        String actionName = insertAction ? "添加" : "修改";
        if (insertAction) {
            user.setCreateid(currentUserLogin().getUserid());
            user.setState(TypeEnums.State.正常.getValue());
            user.setCreatetime(new Date());
            user.setPsd(Md5Util.getMd5(user.getPsd()));
        }
        try {
            if (!StringUtils.isEmpty(deadTime)) {
                user.setDeadline(DateUtil.getDate(deadTime));
            }
            user = userService.save(user);
            addLogInfo(String.format("%s用户%s成功", actionName, user.getUsername()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改);
            return new ReturnModel(true, String.format("%s成功", actionName));
        } catch (Exception e) {
            addLogError(String.format("%s用户%s失败", actionName, user.getUsername()), "", insertAction ? SysLog.LogType.新增 : SysLog.LogType.修改, e, user);
            return new ReturnModel(false, String.format("%s失败", actionName));
        }
    }


    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer id) {
        if (id == null || id <= 0) {
            return new ReturnModel(false, "删除失败，请重新删除");
        }
        try {
            SysUser sysUser = userService.getByID(id);
            if (sysUser == null || sysUser.getId() <= 0) {
                return new ReturnModel(false, "不存在要删除的用户");
            }
            sysUser.setState(TypeEnums.State.删除.getValue());
            userService.save(sysUser);
            addLogInfo(String.format("删除用户%s成功", sysUser.getUsername()), "", SysLog.LogType.删除);
            return new ReturnModel(true, "删除成功");
        } catch (Exception ex) {
            addLogError("删除用户失败", "", SysLog.LogType.删除, ex, id);
            return new ReturnModel(false, "删除失败");
        }
    }

    /**
     * 解锁用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unLock")
    @ResponseBody
    public Object unLock(Integer id) {
        if (id == null || id <= 0) {
            return new ReturnModel(false, "解锁失败，请重新解锁");
        }
        try {
            SysUser sysUser = userService.getByID(id);
            if (sysUser == null || sysUser.getId() <= 0) {
                return new ReturnModel(false, "用户已不存在");
            }
            sysUser.setLoginerrorcount(0);
            userService.save(sysUser);
            addLogInfo(String.format("解锁用户%s成功", sysUser.getUsername()), "", SysLog.LogType.修改);
            return new ReturnModel(true, "解锁成功");

        } catch (Exception ex) {
            addLogError("解锁用户失败", "", SysLog.LogType.修改, ex, id);
            return new ReturnModel(false, "解锁失败，请稍后再试");
        }
    }

    /**
     * 启用禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/changeState")
    @ResponseBody
    public Object changeState(int id) {
        try {
            SysUser user = userService.getByID(id);
            if (user == null || user.getId() <= 0) {
                return new ReturnModel(false, "操作失败，请重新操作");
            }
            // 本身是正常状态，则当前操作是禁用。
            Boolean disableAction = user.getState() == TypeEnums.State.正常.getValue();
            String actionName = disableAction ? "禁用" : "弃用";
            if (disableAction) {
                user.setState(TypeEnums.State.禁用.getValue());
            } else {
                user.setState(TypeEnums.State.正常.getValue());
            }
            userService.save(user);
            addLogInfo(String.format("%s用户%s成功", actionName, user.getUsername()), "", SysLog.LogType.修改);
            return new ReturnModel(true, String.format("%s成功", actionName));
        } catch (Exception ex) {
            addLogError("启用/禁用用户失败", "", SysLog.LogType.修改, ex, id);
            return new ReturnModel(false, "启用/禁用用户失败,请稍后再试");
        }

    }

    /**
     * 获取已选择角色
     *
     * @param userID
     * @return
     */
    @RequestMapping(value = "/getHasSelectRole")
    @ResponseBody
    public Object getHasSelectRole(int userID) {
        return roleService.getRolesByUserId(userID);
    }

    /**
     * 编辑用户权限
     *
     * @param id      用户id
     * @param roleIds 选择角色id
     * @return
     */
    @FormReSubmitValidation(true)
    @RequestMapping(value = "/addRoles")
    @ResponseBody
    public Object addRoles(int id, int[] roleIds) throws JsonProcessingException {
        List<SysUserrole> roles = new ArrayList<SysUserrole>();
        if (roleIds != null && roleIds.length > 0) {
            for (int i = 0; i < roleIds.length; i++) {
                SysUserrole role = new SysUserrole();
                role.setRoleid(roleIds[i]);
                role.setCreatetime(new Date());
                roles.add(role);
            }
        }
        try {
            SysUser sysUser = userService.getByID(id);
            if (sysUser == null || sysUser.getId() <= 0) {
                return new ReturnModel(false, "授权失败,用户已不存在");
            }
            userRoleService.insertUserRoles(sysUser.getId(), roles);
            addLogInfo(String.format("对用户%s授权成功", sysUser.getUsername()), "", SysLog.LogType.其他);
            return new ReturnModel(true, "用户授权成功");
        } catch (Exception e) {
            addLogError(String.format("对用户%s授权成功", id), "", SysLog.LogType.其他, e);
            return new ReturnModel(false, "授权失败,请稍后再试");
        }
    }

    /**
     * 检查是否存在相同登录名
     *
     * @param userId   用户Id
     * @param username 登录名
     * @return Object 如果不存在返回true,否则返回false
     */
    @RequestMapping(value = "/checkSameLoginName", method = RequestMethod.GET)
    @ResponseBody
    public Object checkSameLoginName(Integer userId, String username) {
        if (StringUtils.isEmpty(username)) {
            return true;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("username", username);
        queryMap.put("excludeId", userId);
        return userService.selectCount(queryMap) <= 0;
    }

    /**
     * 对部门排序
     *
     * @param deptList
     * @return
     */
    private List<SysDepartment> sortDept(List<SysDepartment> deptList) {
        Collections.sort(deptList, (obj0, obj1) -> {
            int flag = obj0.getDcode().compareTo(obj1.getDcode());
            if (flag == 0) {
                return obj0.getCreatetime().compareTo(obj1.getCreatetime());
            } else {
                return flag;
            }
        });
        return deptList;
    }
}

