package com.zx.system.service.impl;

import com.zx.base.enums.TypeEnums;
import com.zx.system.dao.SysRoleDao;
import com.zx.system.dao.SysRolemoduleDao;
import com.zx.system.model.SysRole;
import com.zx.system.model.SysRolemodule;
import com.zx.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author feidengke
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    /**
     * Field description
     */
    @Resource
    private SysRoleDao sysRoleDao;
    @Resource
    private SysRolemoduleDao sysRolemoduleDao;

    @Override
    public SysRole selectById(int id) {
        return sysRoleDao.selectById(id);
    }

    @Override
    public int selectCount(Map map) {
        return sysRoleDao.selectCount(map);
    }

    @Override
    public SysRole update(SysRole role) {
        if (role.getId() != null) {
            sysRoleDao.update(role);
        } else {
            sysRoleDao.insert(role);
        }
        return role;
    }

    @Override
    public List<SysRole> getList(Map map) {

        return sysRoleDao.selectList(map);
    }

    @Override
    public int updateRoleModules(Integer roleId, List<SysRolemodule> rmList) {
        sysRolemoduleDao.deleteByRoleId(roleId);
        return sysRolemoduleDao.insertBatch(rmList);
    }

    @Override
    public Boolean roleIsExisted(String name, Integer id) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name",name);
        map.put("excludeId",id);
        map.put("state", TypeEnums.State.正常.getValue());
        return selectCount(map) > 0;
    }

    @Override
    public List<SysRole> getRolesByUserId(Integer userId) {
        return sysRoleDao.getListByUserId(userId);
    }
}