package com.zx.system.service;

import com.zx.system.model.SysRole;
import com.zx.system.model.SysUser;
import com.zx.system.model.SysUserrole;

import java.util.List;


/**
 * 描述 : 用户角色服务接口
 *
 * @author feidengke
 * @version 2017/12/06
 */
public interface UserRoleService {
    /**
     * 批量修改用户角色
     * @param userid
     * @param roles
     * @return
     */
    int insertUserRoles(int userid, List<SysUserrole> roles);

    /**
     * 获取用户角色关联角色id
     *
     * @param user
     * @return
     */
    List<SysRole> getUserRoles(SysUser user);
}
