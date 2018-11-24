package com.zx.system.dao;

import com.zx.system.model.SysUserrole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 舆情系统用户表 数据库操作接口
 * @author wangx
 */
@Repository("sysUserRoleDao")
@org.apache.ibatis.annotations.Mapper
public interface SysUserRoleDao {
    /**
     * 插入角色
     *
     * @param roles
     * @return
     */
    int insertBatch(List<SysUserrole> roles);
    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    int  deleteByUserId(Integer id);
}
