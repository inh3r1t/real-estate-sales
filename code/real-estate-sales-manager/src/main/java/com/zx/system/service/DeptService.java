package com.zx.system.service;

import com.zx.system.model.SysDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门管理
 *
 * @author feidengke
 * @version 2017-12-04 1.0.0
 */
@Repository
public interface DeptService {
    List<SysDepartment> getSubsetsBranchByCode(String code);

    List<SysDepartment> getSubDepartments(List<SysDepartment> all, String deptcode);

    List<SysDepartment> getSubDepartments(String deptcode);

    int update(SysDepartment sysDepartment);

    SysDepartment selectById(Integer id);

    SysDepartment selectByCode(String code);

    int deleteListByCode(String deptcode);

    SysDepartment deptIsExisted(String name, String id);
}
