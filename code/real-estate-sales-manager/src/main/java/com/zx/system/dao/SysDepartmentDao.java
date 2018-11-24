package com.zx.system.dao;

import com.zx.system.model.SysDepartment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库操作接口
 * @author wangx
 */
@Repository("sysDepartmentDao")
public interface SysDepartmentDao {


    /**
     * 插入记录
     */
    int insert(SysDepartment sysDepartment);

    /**
     * 批量插入记录
     */
    int insertBatch(List<SysDepartment> sysDepartmentList);

    /**
     * 更新
     */
    int update(SysDepartment sysDepartment);

    /**
     * 根据Id删除
     */
    int deleteById(Integer id);

    /**
     * 根据Id获取对象
     */
    SysDepartment selectById(Integer id);

    /**
     * 根据code获取对象
     */
    SysDepartment selectByCode(String code);

    /**
     * 查询当前单位所有子节点
     *
     * @param code
     * @return
     */
    List<SysDepartment> getSubsetsBranchByCode(@Param("code") String code);

    /**
     * 获取一级子集
     *
     * @param code
     * @return
     */
    List<SysDepartment> getSubDepartments(@Param("code") String code);

    /**
     * 按照code删除部门和子集
     *
     * @param code
     * @return
     */
    int deleteListByCode(@Param("code") String code);

    /**
     * 判断部门名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    SysDepartment isExisted(@Param("name") String name, @Param("id") String id);

}
