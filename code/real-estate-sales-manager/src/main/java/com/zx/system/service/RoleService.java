package com.zx.system.service;

import com.zx.system.model.SysRole;
import com.zx.system.model.SysRolemodule;

import java.util.List;
import java.util.Map;

/**
 * @author feidengke
 */
public interface RoleService {
    /**
     * 根据ID查询SysRole
     *
     * @param id
     * @return
     */
    SysRole selectById(int id);

    /**
     * 查询SysRole总数
     *
     * @param map
     * @return
     */
    int selectCount(Map map);

    /**
     * 更新SysRole
     *
     * @param role
     * @return
     */
    SysRole update(SysRole role);

    /**
     * 查询SysRole分页数据
     *
     * @param map
     * @return
     */
    List<SysRole> getList(Map map);

    /**
     * 修改角色对应模块
     *
     * @param roleId
     * @param rmList
     * @return
     */
    int updateRoleModules(Integer roleId, List<SysRolemodule> rmList);

    /**
     * 判断角色名称是否存在
     *
     * @param name 角色名称
     * @param id   角色ID
     * @return Boolean
     */
    Boolean roleIsExisted(String name, Integer id);

    /**
     * 获取用户角色
     *
     * @param userid
     * @return
     */
    List<SysRole> getRolesByUserId(Integer userid);
}
