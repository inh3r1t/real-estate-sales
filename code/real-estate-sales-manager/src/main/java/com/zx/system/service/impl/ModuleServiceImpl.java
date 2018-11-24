package com.zx.system.service.impl;

import com.zx.system.dao.SysModuleDao;
import com.zx.system.model.SysModule;
import com.zx.system.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单
 *
 * @author V.E.
 * @version 1.0, 17/12/4
 */
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    @Resource
    public SysModuleDao sysModuleDao;

    @Override
    public List<SysModule> selectList() {
        return sysModuleDao.selectList();
    }

    @Override
    public List<SysModule> selectModulesByRoleId(Integer roleid) {
        return sysModuleDao.selectModulesByRoleId(roleid);
    }

    @Override
    public List<SysModule> selectModulesByUserId(Integer userid) {
        return sysModuleDao.selectModulesByUserId(userid);
    }


    @Override
    public SysModule selectById(Integer id) {
        return sysModuleDao.selectById(id);
    }

    @Override
    public SysModule update(SysModule sysModule) {
        if (sysModule.getId() != null) {
            sysModuleDao.update(sysModule);
        } else {
            sysModuleDao.insert(sysModule);
        }

        return sysModule;
    }

    @Override
    public SysModule selectByCode(String code) {
        return sysModuleDao.selectByCode(code);
    }

    @Override
    public List<SysModule> selectByIds(List<Integer> ids) {
        return sysModuleDao.selectByIds(ids);
    }

    @Override
    public List<SysModule> getSubModules(String parentCode) {
        return sysModuleDao.getSubModules(parentCode);
    }

    @Override
    public SysModule moduleIsExisted(String name, String id, String mcode, String code) {
        return sysModuleDao.isExisted(name, id, mcode, code);
    }

    @Override
    public int deleteListByCode(String mcode) {
        return sysModuleDao.deleteListByCode(mcode);
    }

}
