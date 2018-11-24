package com.zx.system.service;

import com.zx.system.model.SysLog;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 *
 * @author Xiafl.
 * @version 2017/12/04
 */
public interface LogService {

    /**
     * 根据条件统计数量
     *
     * @param map 条件
     **/
    int selectCount(Map map);

    /**
     * 查询
     *
     * @param map 查询条件
     * @return 日志集合
     **/
    List<SysLog> getList(Map map);

    /**
     * 查询
     *
     * @param id id
     * @return 日志
     **/
    SysLog selectById(int id);

    /**
     * 新增
     *
     * @param log
     * @return 影响的条数
     **/
    int insert(SysLog log);

    /**
     * 清空
     *
     * @return 删除的条数
     **/
    int deleteAll();
}
