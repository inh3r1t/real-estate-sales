package com.zx.system.service.impl;

import com.zx.base.common.AuthUtil;
import com.zx.base.enums.TypeEnums;
import com.zx.lib.utils.DateUtil;
import com.zx.lib.utils.StringUtil;
import com.zx.lib.utils.encrypt.Base64Util;
import com.zx.system.dao.SysUserDao;
import com.zx.system.dao.SysUserloginDao;
import com.zx.system.model.*;
import com.zx.system.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理业务逻辑类
 *
 * @author wangx
 * @version 2017-12-04 1.0.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    public SysUserloginDao sysUserloginDao;

    @Resource
    public SysUserDao sysUserDao;

    @Resource
    private DeptService deptService;

    @Resource
    private ModuleService moduleService;

    @Resource
    private RoleService roleService;

    @Override
    public int selectCount(Map<String, Object> paramMap) {
        return sysUserDao.selectRowCount(paramMap);
    }

    @Override
    public List<SysUser> getList(Map paramMap) {
        List<SysUser> list = sysUserDao.selectList(paramMap);
        for (SysUser sysUser : list) {
            sysUser.setSysRoles(roleService.getRolesByUserId(sysUser.getId()));
        }
        return list;
    }


    @Override
    public SysUser getByUserName(String userName) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("username", userName);
        return sysUserDao.selectUser(paramMap);
    }

    /**
     * 登录并记录相关登录信息
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public SysUserlogin login(String username, String password) {
        SysUserlogin loginInfo = new SysUserlogin();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("username", username);
        paramMap.put("psd", password);
        SysUser sysUser = sysUserDao.selectUser(paramMap);
        loginInfo.setUser(sysUser);
        if (sysUser != null && sysUser.getState() == TypeEnums.State.正常.getValue()) {
            loginInfo.setUserid(sysUser.getId());
            loginInfo.setUsername(sysUser.getUsername());
            loginInfo.setBranchcode(sysUser.getBranchcode());
            loginInfo.setRealname(sysUser.getFullname());
            loginInfo.setLoginphoto(sysUser.getPhoto());
            loginInfo.setLogintoken(Base64Util.encryptToStr(sysUser.getId() + "_" + sysUser.getUsername() + "_" + DateUtil.getNowTimestamp()));
            //添加部门
            if (!StringUtil.isEmpty(sysUser.getBranchcode())) {
                SysDepartment dept = deptService.selectByCode(sysUser.getBranchcode());
                if (dept != null) {
                    loginInfo.setDepartment(dept);
                    loginInfo.setBranchname(dept.getDname());
                }
            }
            List<SysRole> roles = roleService.getRolesByUserId(sysUser.getId());
            if (roles != null && roles.size() > 0) {
                //添加角色
                loginInfo.setRoles(roles);
                String names = "";
                for (SysRole item :
                        roles) {
                    names = names + (!names.isEmpty() ? " | " : "") + item.getName();
                }
                loginInfo.setRolenames(names);
                //判断是否超管
                loginInfo.setIssuper(AuthUtil.isAdmin(roles));
                //添加菜单
                if (loginInfo.getIssuper()) {
                    loginInfo.setModules(moduleService.selectList());
                } else {
                    List<SysModule> modules = moduleService.selectModulesByUserId(sysUser.getId());
                    loginInfo.setModules(modules);
                }
            }
            sysUser.setPrelogintime(sysUser.getLastlogintime());
            sysUser.setLastlogintime(DateUtil.getCalendarNow().getTime());
            loginInfo.setCreatetime(sysUser.getLastlogintime());
            loginInfo.setPrelogintime(sysUser.getPrelogintime());
            save(sysUser);
        }
        return loginInfo;
    }

    @Override
    public SysUserlogin selectByToken(String logintoken) {
        return sysUserloginDao.selectByLogintoken(logintoken);
    }

    @Override
    public List<SysUserlogin> selectByToken(List<String> loginTokens) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("IncludeLogintoken", loginTokens);
        return sysUserloginDao.selectList(paramsMap);
    }

    @Override
    public int addUserLogin(SysUserlogin userlogin) {
        return sysUserloginDao.insert(userlogin);
    }

    @Override
    public void deleteUserLoginByToken(String token) {
        sysUserloginDao.deleteByLogintoken(token);
    }

    @Override
    public SysUser getByID(Integer id) {
        SysUser sysUser = sysUserDao.selectById(id);
        if (sysUser != null) {
            List<SysRole> roles = roleService.getRolesByUserId(id);
            sysUser.setSysRoles(roles);
        }
        return sysUser;
    }

    /**
     * 添加或修改用户
     *
     * @param user
     * @return
     */

    @Override
    public SysUser save(SysUser user) {
        if (user.getId() != null && user.getId() != 0) {
            sysUserDao.update(user);
        } else {
            sysUserDao.insert(user);
        }

        return user;
    }

}
