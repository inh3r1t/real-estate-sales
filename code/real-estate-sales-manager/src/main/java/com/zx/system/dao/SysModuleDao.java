package com.zx.system.dao;

import com.zx.system.model.SysModule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限控制系统用户可以访问的功能，包含菜单、页面、页面上的功能按钮、虚拟的功能操作等 数据库操作接口
 * @author wangx
 */
@Repository("sysModuleDao")
public interface SysModuleDao {


    /**
     * 插入记录
     */
    int insert(SysModule sysModule);

    /**
     * 更新
     */
    int update(SysModule sysModule);

    /**
     * 根据Id删除
     */
    int deleteById(Integer id);

    /**
     * 根据Id获取对象
     */
    SysModule selectById(Integer id);

    /**
     * 查询菜单所有列表
     */
    List<SysModule> selectList();

    /**
     * 获取角色下所有菜单
     *
     * @param roleid
     * @return
     */
    List<SysModule> selectModulesByRoleId(Integer roleid);

    SysModule selectByCode(String code);

    List<SysModule> selectByIds(List<Integer> ids);

    List<SysModule> getSubModules(String code);

    SysModule isExisted(@Param("name") String name, @Param("id") String id, @Param("mcode") String mcode, @Param("code") String code);

    int deleteListByCode(@Param("code") String code);

    List<SysModule> selectModulesByUserId(Integer userid);
}
