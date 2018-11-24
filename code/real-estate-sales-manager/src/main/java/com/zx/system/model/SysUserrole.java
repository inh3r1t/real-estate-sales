package com.zx.system.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @author wangx
 */
public class SysUserrole implements Serializable {
    private Integer id;
    private Integer userid;
    private Integer roleid;
    private Date createtime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getRoleid() {
        return this.roleid;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }


}
