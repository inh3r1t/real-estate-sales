package com.zx.system.dao;

import com.zx.system.model.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 舆情系统用户表 数据库操作接口
 *
 * @author wangx
 */
@Repository("sysUserDao")
public interface SysUserDao {

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    SysUser selectById(Integer id);

    /**
     * 插入记录
     *
     * @param user
     * @return
     */
    int insert(SysUser user);

    /**
     * 更新SysUser
     *
     * @param user
     * @return
     */
    int update(SysUser user);

    /**
     * 查询
     *
     * @param map
     * @return
     */
    int selectRowCount(Map map);

    /**
     * 查询
     *
     * @param map
     * @return
     */
    SysUser selectUser(Map map);

    /**
     * 查询
     *
     * @param map
     * @return
     */
    List<SysUser> selectList(Map map);

}
