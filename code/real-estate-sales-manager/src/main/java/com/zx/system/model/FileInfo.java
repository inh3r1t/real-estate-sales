package com.zx.system.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 文件管理实体类
 *
 * @author wangx
 * @version 2017-12-04 1.0.0
 */
@SuppressWarnings("serial")
public class FileInfo implements Serializable {

    private Integer id;
    private String name;
    private Integer groupid;
    private String suffix;
    private Double size;
    private String path;
    private String md5;
    private Integer createrid;
    private Date createtime;
    private Integer state;
    private String remark;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getGroupid() {
        return this.groupid;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getSize() {
        return this.size;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setCreaterid(Integer createrid) {
        this.createrid = createrid;
    }

    public Integer getCreaterid() {
        return this.createrid;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }


}
