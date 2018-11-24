package com.zx.system.service.impl;

import com.zx.system.dao.SysLogDao;
import com.zx.system.model.SysLog;
import com.zx.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统日志接口实现
 *
 * @author Xiafl.
 * @version 2017/12/04
 */
@Service("logService")
public class LogServiceImpl implements LogService {
    /**
     * DAO
     *
     **/
    @Autowired
    private SysLogDao sysLogDao;

    /**
     * 根据条件统计数量
     *
     * @param map 条件
     **/
    @Override
    public int selectCount(Map map) {
        return sysLogDao.selectCount(map);
    }

    /**
     * 查询
     *
     * @param map 查询条件
     **/
    @Override
    public List<SysLog> getList(Map map) {
        return sysLogDao.selectList(map);
    }

    /**
     * 查询
     *
     * @param id id
     **/
    @Override
    public SysLog selectById(int id) {
        return sysLogDao.selectById(id);
    }

    /**
     * 新增
     *
     * @param log
     **/
    @Override
    public int insert(SysLog log) {
        return sysLogDao.insert(log);
    }

    /**
     * 清空
     **/
    @Override
    public int deleteAll() {
        return sysLogDao.deleteAll();
    }


}
