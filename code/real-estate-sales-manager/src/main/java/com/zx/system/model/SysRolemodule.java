package com.zx.system.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangx
 */
@SuppressWarnings("serial")
public class SysRolemodule implements Serializable {

    private Integer id;
    private Integer roleid;
    private Integer moduleid;
    private Date createtime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getRoleid() {
        return this.roleid;
    }

    public void setModuleid(Integer moduleid) {
        this.moduleid = moduleid;
    }

    public Integer getModuleid() {
        return this.moduleid;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }


}
