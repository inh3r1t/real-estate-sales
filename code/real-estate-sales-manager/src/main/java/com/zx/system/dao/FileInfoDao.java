package com.zx.system.dao;

import com.zx.system.model.FileInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库操作接口
 *
 * @author wangx
 */
@Repository("fileInfoDao")
public interface FileInfoDao {

    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 查询所有文件信息
     */
    List<FileInfo> selectList();

    /**
     * 插入文件信息
     *
     * @param fileInfo
     * @return
     */
    Integer insert(FileInfo fileInfo);

    /**
     * 根据id查找文件
     *
     * @param id
     * @return
     */
    FileInfo selectById(Integer id);

    /**
     * 修改文件
     *
     * @param fileInfo
     * @return
     */
    Integer update(FileInfo fileInfo);

    int deleteByGroupId(Integer groupid);

    int insertBatch(List<FileInfo> fileInfos);
}
