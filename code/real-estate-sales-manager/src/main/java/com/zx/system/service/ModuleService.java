package com.zx.system.service;

import com.zx.system.model.SysModule;

import java.util.List;

/**
 * Interface description
 *
 * @author V.E.
 * @version 1.0, 17/12/4
 */
public interface ModuleService {
    List<SysModule> selectList();

    List<SysModule> selectModulesByRoleId(Integer roleid);

    List<SysModule> selectModulesByUserId(Integer userid);

    SysModule selectById(Integer id);

    SysModule update(SysModule sysModule);

    SysModule selectByCode(String parentCode);

    List<SysModule> selectByIds(List<Integer> ids);

    List<SysModule> getSubModules(String parentCode);

    SysModule moduleIsExisted(String name, String id, String mcode, String code);

    int deleteListByCode(String mcode);

}

