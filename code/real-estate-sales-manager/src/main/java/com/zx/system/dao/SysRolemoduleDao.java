package com.zx.system.dao;

import com.zx.system.model.SysRolemodule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色所拥有的权限配置表，一个角色有若干权限 数据库操作接口
 *
 * @author wanght
 * @version 2017-12-05 1.0.0
 */
@Repository("sysRolemoduleDao")
public interface SysRolemoduleDao {
    /**
     * 插入记录
     *
     * @param sysRolemodule
     * @return
     */
    int insert(SysRolemodule sysRolemodule);

    /**
     * 批量插入记录
     *
     * @param sysRolemoduleList
     * @return
     */
    int insertBatch(List<SysRolemodule> sysRolemoduleList);

    /**
     * 根据Id更新
     *
     * @param sysRolemodule
     * @return
     */
    int updateById(SysRolemodule sysRolemodule);

    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据Id获取对象
     *
     * @param id
     * @return
     */
    SysRolemodule selectById(Integer id);

    /**
     * 按照角色id删除模块
     *
     * @param roleId
     * @return
     */
    int deleteByRoleId(Integer roleId);

}
