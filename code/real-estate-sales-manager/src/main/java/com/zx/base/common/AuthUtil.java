package com.zx.base.common;

import com.zx.system.model.SysRole;

import java.util.List;

/**
 * 用户权限验证工具类
 *
 * @author V.E.
 * @version 2017/12/06
 */
public class AuthUtil {

    /**
     * 是否是超级管理员
     * @param roles
     * @return
     */
    public static boolean isAdmin(List<SysRole> roles){
        for (SysRole sysRole : roles) {
            if(sysRole.getIsadmin()){
                return true;
            }
        }
        return false;
    }
}
