package com.zx.system.service;

import com.zx.system.model.FileInfo;

import java.util.List;

/**
 * 业务接口
 *
 * @author wangnx
 */
public interface FileInfoService {
    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 添加
     *
     * @param fileInfo
     * @return
     */
    Integer insert(FileInfo fileInfo);

    /**
     * 查询所有
     *
     * @return
     */
    List<FileInfo> selectList();

    /**
     * 根据Id查找
     *
     * @param id
     * @return
     */
    FileInfo selectById(Integer id);
    /**
     * 修改
     *
     * @param fileInfo
     * @return
     */
    Integer update(FileInfo fileInfo);

    int deleteByGroupId(Integer id);
}