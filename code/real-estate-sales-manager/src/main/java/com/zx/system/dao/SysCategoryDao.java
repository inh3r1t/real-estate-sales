package com.zx.system.dao;

import com.zx.system.model.SysCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库操作接口
 * @author wangx
 */
@Repository("sysCategoryDao")
public interface SysCategoryDao {

    /**
     * 按照类别查询分组数据
     *
     * @param type
     * @return
     */
    List<SysCategory> selectListByType(Integer type);

    /**
     * 修改分组
     *
     * @param sysCategory
     * @return
     */
    int update(SysCategory sysCategory);

    /**
     * 新增分组
     *
     * @param sysCategory
     * @return
     */
    int insert(SysCategory sysCategory);

    /**
     * 按照主键查找分组
     *
     * @param id
     * @return
     */
    SysCategory selectById(Integer id);

    /**
     * 按照code查找分组
     *
     * @param code
     * @return
     */
    SysCategory selectByCode(@Param("code") String code);

    /**
     * 根据code删除分组及子集
     *
     * @param code
     * @return
     */
    int deleteListByCode(@Param("code") String code);

    /**
     * 获取一级子集
     *
     * @param code
     * @return
     */
    List<SysCategory> getSubCategories(@Param("code") String code);

    /**
     * 按照类别获取子集
     *
     * @param code
     * @param type
     * @return
     */
    List<SysCategory> getSubsetsBranchByCode(@Param("code") String code, @Param("type") int type);

    /**
     * 判断分组名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    SysCategory isExisted(@Param("name") String name, @Param("id") String id);
}
