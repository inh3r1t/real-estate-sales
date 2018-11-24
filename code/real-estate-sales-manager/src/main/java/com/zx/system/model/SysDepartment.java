package com.zx.system.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangx
 */
@SuppressWarnings("serial")
public class SysDepartment implements Serializable {

    private Integer id;

    private String dcode;

    /**
     * 部门名称
     */
    @NotBlank
    @Size(max = 50, message = "部门名称最大长度不能超过{max}")
    private String dname;
    private String parentcode;

    /**
     * 经度
     */
    private Float longitude;

    /**
     * 纬度
     */
    private Float latitude;

    /**
     * 状态(-1删除 0默认 1禁用)
     */
    private Integer state;

    /**
     * 备注
     */
    @Size(max = 100, message = "备注最大长度不能超过{max}")
    private String remark;
    private Date createtime;

    /**
     * 部门别名
     */
    @Size(max = 50, message = "部门别名最大长度不能超过{max}")
    private String dnameother;

    /**
     * 显示排序
     */
    private int displayorder;
    private Integer createrid;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }

    public String getDcode() {
        return this.dcode;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDname() {
        return this.dname;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getParentcode() {
        return this.parentcode;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLatitude() {
        return this.latitude;
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

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setDnameother(String dnameother) {
        this.dnameother = dnameother;
    }

    public String getDnameother() {
        return this.dnameother;
    }

    public void setDisplayorder(int displayorder) {
        this.displayorder = displayorder;
    }

    public int getDisplayorder() {
        return this.displayorder;
    }


    public Integer getCreaterid() {
        return createrid;
    }

    public void setCreaterid(Integer createrid) {
        this.createrid = createrid;
    }
}
