package com.zx.system.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @author wangx
 */
public class SysUserlogin implements Serializable {

    private Integer id;
    private String logintoken;
    private Integer userid;
    private String userareacode;
    private String userareaname;
    private String branchcode;
    private String branchname;
    private String username;
    private String realname;
    private String loginphoto;
    private String clientip;
    private String rolenames;
    private Boolean issuper;
    private Date createtime;
    private Date prelogintime;
    private Date lastaccesstime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogintoken(String logintoken) {
        this.logintoken = logintoken;
    }

    public String getLogintoken() {
        return this.logintoken;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserareacode(String userareacode) {
        this.userareacode = userareacode;
    }

    public String getUserareacode() {
        return this.userareacode;
    }

    public void setUserareaname(String userareaname) {
        this.userareaname = userareaname;
    }

    public String getUserareaname() {
        return this.userareaname;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getBranchcode() {
        return this.branchcode;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBranchname() {
        return this.branchname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setLoginphoto(String loginphoto) {
        this.loginphoto = loginphoto;
    }

    public String getLoginphoto() {
        return this.loginphoto;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public String getClientip() {
        return this.clientip;
    }

    public void setRolenames(String rolenames) {
        this.rolenames = rolenames;
    }

    public String getRolenames() {
        return this.rolenames;
    }

    public void setIssuper(Boolean issuper) {
        this.issuper = issuper;
    }

    public Boolean getIssuper() {
        return this.issuper;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setPrelogintime(Date prelogintime) {
        this.prelogintime = prelogintime;
    }

    public Date getPrelogintime() {
        return this.prelogintime;
    }

    public void setLastaccesstime(Date lastaccesstime) {
        this.lastaccesstime = lastaccesstime;
    }

    public Date getLastaccesstime() {
        return this.lastaccesstime;
    }

    /**
     * 用户信息
     **/
    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    /**
     * 权限模块
     **/
    private List<SysModule> modules;

    public List<SysModule> getModules() {
        return modules;
    }

    public void setModules(List<SysModule> modules) {
        this.modules = modules;
    }

    /**
     * 登录用户角色
     */
    private List<SysRole> roles;

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    /**
     * 部门信息
     **/
    private SysDepartment department;

    public SysDepartment getDepartment() {
        return department;
    }

    public void setDepartment(SysDepartment department) {
        this.department = department;
    }

}
