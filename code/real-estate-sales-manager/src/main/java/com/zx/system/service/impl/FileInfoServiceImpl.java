package com.zx.system.service.impl;

import com.zx.system.dao.FileInfoDao;
import com.zx.system.model.FileInfo;
import com.zx.system.service.FileInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 文件管理业务接口实现
 *
 * @author V.E.
 */
@Service("fileInfoService")
public class FileInfoServiceImpl implements FileInfoService {
    @Resource
    private FileInfoDao fileInfoDao;

    @Override
    public Integer deleteById(Integer id) {
        return null;
    }

    @Override
    public Integer insert(FileInfo fileInfo) {
        return fileInfoDao.insert(fileInfo);
    }

    @Override
    public List<FileInfo> selectList() {
        return fileInfoDao.selectList();
    }

    @Override
    public FileInfo selectById(Integer id) {
        return fileInfoDao.selectById(id);
    }

    @Override
    public Integer update(FileInfo fileInfo) {
        return fileInfoDao.update(fileInfo);
    }
}