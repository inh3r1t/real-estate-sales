package com.zx.system.service.impl;

import com.zx.system.dao.SysCategoryDao;
import com.zx.system.model.SysCategory;
import com.zx.system.service.SysCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 业务接口实现
 * @author wangx
 */
@Service("sysCategoryService")
public class SysCategoryServiceImpl implements SysCategoryService {

    @Resource
    private SysCategoryDao sysCategoryDao;

    @Override
    public List<SysCategory> selectListByType(Integer type) {
        return sysCategoryDao.selectListByType(type);
    }

    @Override
    public List<SysCategory> getSubsetsBranchByCode(String code, int type) {
        return sysCategoryDao.getSubsetsBranchByCode(code == null ? "" : code, type);
    }

    @Override
    public List<SysCategory> getSubCategories(String code) {
        return sysCategoryDao.getSubCategories(code);
    }

    @Override
    public int update(SysCategory sysCategory) {
        if (sysCategory.getId() != null) {
            return sysCategoryDao.update(sysCategory);
        } else {
            return sysCategoryDao.insert(sysCategory);
        }
    }

    @Override
    public SysCategory selectById(Integer id) {
        return sysCategoryDao.selectById(id);
    }

    @Override
    public SysCategory selectByCode(String code) {
        return sysCategoryDao.selectByCode(code);
    }

    @Override
    public int deleteListByCode(String code) {
        return sysCategoryDao.deleteListByCode(code);
    }

    /**
     * @param name
     * @param id
     * @return
     */
    @Override
    public SysCategory deptIsExisted(String name, String id) {
        return sysCategoryDao.isExisted(name, id);
    }
}