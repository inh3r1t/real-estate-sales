package com.zx.system.service;

import com.zx.system.model.SysCategory;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 业务接口
 *
 * @author wangx
 */
@Repository
public interface SysCategoryService {

    /**
     * 根据type查询集合（默认分类,任务分类）
     *
     * @param type
     * @return
     */
    List<SysCategory> selectListByType(Integer type);

    /**
     * 根据type获取子节点code
     *
     * @param code
     * @param type
     * @return
     */
    List<SysCategory> getSubsetsBranchByCode(String code, int type);


    /**
     * 获取子级分类
     *
     * @param deptcode
     * @return
     */
    List<SysCategory> getSubCategories(String deptcode);

    /**
     * 修改
     *
     * @param sysCategory
     * @return
     */
    int update(SysCategory sysCategory);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    SysCategory selectById(Integer id);

    /**
     * 根据code查询
     *
     * @param code
     * @return
     */
    SysCategory selectByCode(String code);

    /**
     * 删除节点
     *
     * @param deptcode
     * @return
     */
    int deleteListByCode(String deptcode);

    /**
     * 判断重复
     *
     * @param name
     * @param id
     * @return
     */

    SysCategory deptIsExisted(String name, String id);


}