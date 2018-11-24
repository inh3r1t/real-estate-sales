package com.zx.system.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangx
 */

@SuppressWarnings("serial")
public class SysCategory implements Serializable {

    private Integer id;

    @Size(max = 30)
    private String code;

    /**
     * 分类名称
     */
    @NotBlank
    @Size(max = 50, message = "分类名称最大长度不能超过{max}")
    private String name;

    /**
     * 分类别名
     */
    @Size(max = 50, message = "分类别名最大长度不能超过{max}")
    private String nameother;

    private Integer createrid;
    /**
     * 分组类型(1通用分组2私有分组)
     */
    private Integer type;

    private Date createtime;

    /**
     * 状态(-1删除 0默认 1禁用)
     */
    private Integer state;

    /**
     * 备注
     */
    @Size(max = 100, message = "备注最大长度不能超过{max}")
    private String remark;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNameother(String nameother) {
        this.nameother = nameother;
    }

    public String getNameother() {
        return this.nameother;
    }

    public void setCreaterid(Integer createrid) {
        this.createrid = createrid;
    }

    public Integer getCreaterid() {
        return this.createrid;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
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
