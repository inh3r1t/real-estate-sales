package com.zx.system.service.impl;

import com.zx.system.dao.SysDepartmentDao;
import com.zx.system.model.SysDepartment;
import com.zx.system.service.DeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author feidengke
 * @date 2017/12/4
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {
    @Resource
    private SysDepartmentDao sysDepartmentDao;

    @Override
    public List<SysDepartment> getSubsetsBranchByCode(String code) {
        return sysDepartmentDao.getSubsetsBranchByCode(code == null ? "" : code);
    }

    @Override
    public List<SysDepartment> getSubDepartments(List<SysDepartment> all, String deptCode) {
        return all.stream().filter(__ -> __.getDcode().startsWith(deptCode)
                && __.getDcode().length() == deptCode.length() + 3)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysDepartment> getSubDepartments(String code) {
        return sysDepartmentDao.getSubDepartments(code);
    }

    @Override
    public int update(SysDepartment sysDepartment) {
        if (sysDepartment.getId() != null) {
            return sysDepartmentDao.update(sysDepartment);
        } else {
            return sysDepartmentDao.insert(sysDepartment);
        }
    }

    @Override
    public SysDepartment selectById(Integer id) {
        return sysDepartmentDao.selectById(id);
    }

    @Override
    public SysDepartment selectByCode(String code) {
        return sysDepartmentDao.selectByCode(code);
    }

    @Override
    public int deleteListByCode(String deptCode) {
        return sysDepartmentDao.deleteListByCode(deptCode);
    }

    @Override
    public SysDepartment deptIsExisted(String name, String id) {
        return sysDepartmentDao.isExisted(name, id);
    }
}
