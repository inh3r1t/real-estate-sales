package com.zx.system.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangx
 */
@SuppressWarnings("serial")
public class SysRole implements Serializable {

    private Integer id;
    /**
     * 角色名
     **/
    @NotBlank(message = "角色名不能为空")
    @Size(max = 20,min = 2,message = "角色名长度为 {min}-{max} 个字符串")
    private String name;
    /**
     * 状态 0=正常 -1=删除  1=禁用
     **/
    private Integer state;
    /**
     * 排序字段
     **/
    private Integer sortnum;
    /**
     * 创建人id
     **/
    private Integer createid;
    /**
     * 创建时间
     **/
    private Date createtime;
    /**
     * 备注
     **/
    @Size(max = 100, message = "备注长度最大{max}位")
    private String remark;
    /**
     * 是否超管
     **/
    private Boolean isadmin;

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

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public Integer getSortnum() {
        return this.sortnum;
    }

    public void setCreateid(Integer createid) {
        this.createid = createid;
    }

    public Integer getCreateid() {
        return this.createid;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setIsadmin(Boolean isadmin) {
        this.isadmin = isadmin;
    }

    public Boolean getIsadmin() {
        return this.isadmin;
    }


}
