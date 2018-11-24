package com.zx.base.model;

import java.io.Serializable;

/**
 * 文件对象
 *
 * @author V.E.
 * @version 2017/12/11
 */

@SuppressWarnings("serial")
public class FileBean implements Serializable {
    private String filePath;
    private String fileName;
    private String fileSize;
    private String fileType;
    private String fileLastModifiedTime;

    public FileBean() {}

    public FileBean(String filePath, String fileName, String fileSize,
                    String fileType,String fileLastModifiedTime) {
        super();
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileLastModifiedTime = fileLastModifiedTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public String getFileLastModifiedTime() {
        return fileLastModifiedTime;
    }

    public void setFileLastModifiedTime(String fileLastModifiedTime) {
        this.fileLastModifiedTime = fileLastModifiedTime;
    }
}
