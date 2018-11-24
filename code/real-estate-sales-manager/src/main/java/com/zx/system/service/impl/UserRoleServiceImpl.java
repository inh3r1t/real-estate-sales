package com.zx.system.service.impl;

import com.zx.system.dao.SysRoleDao;
import com.zx.system.dao.SysUserRoleDao;
import com.zx.system.model.SysRole;
import com.zx.system.model.SysUser;
import com.zx.system.model.SysUserrole;
import com.zx.system.service.ModuleService;
import com.zx.system.service.RoleService;
import com.zx.system.service.UserRoleService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色
 *
 * @author wangx
 * @version 1.0, 17/12/7
 */
@Service("userRoleService")
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    public SysUserRoleDao sysUserRoleDao;

    @Resource
    private ModuleService moduleService;

    @Resource
    private RoleService roleService;

    @Resource
    private SysRoleDao sysRoleDao;


    @Override
    public int insertUserRoles(int userid, List<SysUserrole> roles) {

        for (int i = 0; i < roles.size(); i++) {
            roles.get(i).setUserid(userid);
        }

        sysUserRoleDao.deleteByUserId(userid);
        if (roles.size() > 0) {
            sysUserRoleDao.insertBatch(roles);
        }

        return 0;
    }


    @Override
    public List<SysRole> getUserRoles(SysUser user) {
        List<SysRole> list = sysRoleDao.getListByUserId(user.getId());
        return list;
    }

}
