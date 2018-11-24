package com.zx.base.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件夹对象
 *
 * @author V.E.
 * @version 2017/12/11
 */

@SuppressWarnings("serial")
public class DirBean  implements Serializable {
    private String dirPath;
    private String prevPath;
    private String dirSize;
    private int dirCount;
    List<FileBean> files=new LinkedList<FileBean>();
    public DirBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    public DirBean(String dirPath, String dirSize, int dirCount,
                   List<FileBean> files) {
        super();
        this.dirPath = dirPath;
        this.dirSize = dirSize;
        this.dirCount = dirCount;
        this.files = files;
    }
    public String getDirPath() {
        return dirPath;
    }
    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
    public String getDirSize() {
        return dirSize;
    }
    public void setDirSize(String dirSize) {
        this.dirSize = dirSize;
    }
    public int getDirCount() {
        return dirCount;
    }
    public void setDirCount(int dirCount) {
        this.dirCount = dirCount;
    }
    public List<FileBean> getFiles() {
        return files;
    }
    public void setFiles(List<FileBean> files) {
        this.files = files;
    }

    public String getPrevPath() {
        return prevPath;
    }

    public void setPrevPath(String prevPath) {
        this.prevPath = prevPath;
    }
}
