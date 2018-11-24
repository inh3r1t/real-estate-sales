package com.zx.system.dao;

import com.zx.system.model.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 记录系统常规操作日志；日志应按照重要程度定时删除； 数据库操作接口
 *
 * @author Xiafl.
 * @version 2017/12/04
 */
@Repository
@Mapper
public interface SysLogDao {

    /**
     * 条件统计数量
     *
     * @param map 查询条件
     * @return int
     **/
    int selectCount(Map map);

    /**
     * 条件查询
     *
     * @param map 查询条件
     * @return list
     **/
    List<SysLog> selectList(Map map);

    /**
     * 根据Id获取对象
     *
     * @param id id
     * @return 单个日志对象
     */
    SysLog selectById(Integer id);

    /**
     * 插入记录
     *
     * @param sysLog
     * @return 返回主键或者记录条数
     */
    int insert(SysLog sysLog);

    /**
     * 根据Id删除
     *
     * @param id Id
     * @return 删除的函数
     */
    int deleteById(Integer id);

    /**
     * 全部删除
     *
     * @return 删除的行数
     **/
    int deleteAll();
}
