package com.zx.system.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author wangx
 */
public class SysUser implements Serializable {

    private Integer id;

    /**
     * 用户名\登录名
     **/

    @NotBlank
    @Size(max = 50,min = 2)
    private String username;

    /**
     * 密码
     **/

    @Size(max = 64)
    private String psd;

    /**
     * 姓名
     **/

    @NotBlank(message = "姓名不能为空")
    private String fullname;

    /**
     * 性别
     **/
    private Integer sex;

    /**
     * 出生日期
     **/
    private Date birthday;

    /**
     * 特使编号
     **/
    private String policenum;

    /**
     * 特殊职责
     **/
    private String policeduty;

    /**
     * 手机
     **/
    private String mobile;

    /**
     * 电话
     **/
    private String phone;

    /**
     * 邮箱
     **/
    private String email;

    /**
     * 登录IP
     **/
    private String ip;

    /**
     * 状态 0=正常 -1=删除  1=禁用
     **/
    private Integer state;


    /**
     * 过期日期
     **/
    private Date deadline;

    /**
     * 创建时间
     **/
    private Date createtime;

    /**
     * 上一次登录时间
     **/
    private Date prelogintime;

    /**
     * 最后一次登录时间
     **/
    private Date lastlogintime;

    /**
     * 备注/说明
     **/
    @Size(max = 100, message = "备注长度最大{max}位")
    private String remark;

    /**
     * 头像图片
     **/
    private String photo;

    /**
     * 登录次数
     **/
    private Integer logoncount;

    /**
     * 部门编码
     **/
    private String branchcode;

    /**
     * 部门名称
     **/
    private String branchname;

    /**
     * 创建人ID
     **/
    private Integer createid;

    /**
     * 最后登录错误时间
     **/
    private Date lastloginerrortime;

    /**
     * 登录错误次数
     **/
    private Integer loginerrorcount;

    public Date getLastloginerrortime() {
        return lastloginerrortime;
    }

    public void setLastloginerrortime(Date lastloginerrortime) {
        this.lastloginerrortime = lastloginerrortime;
    }

    public Integer getLoginerrorcount() {
        return loginerrorcount;
    }

    public void setLoginerrorcount(Integer loginerrorcount) {
        this.loginerrorcount = loginerrorcount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPolicenum() {
        return policenum;
    }

    public void setPolicenum(String policenum) {
        this.policenum = policenum;
    }

    public String getPoliceduty() {
        return policeduty;
    }

    public void setPoliceduty(String policeduty) {
        this.policeduty = policeduty;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getState() {
        return state;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPrelogintime() {
        return prelogintime;
    }

    public void setPrelogintime(Date prelogintime) {
        this.prelogintime = prelogintime;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getLogoncount() {
        return logoncount;
    }

    public void setLogoncount(Integer logoncount) {
        this.logoncount = logoncount;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public Integer getCreateid() {
        return createid;
    }

    public void setCreateid(Integer createid) {
        this.createid = createid;
    }

    private SysDepartment sysDepartment;

    private List<SysRole> sysRoles;

    private List<SysModule> modules;

    public SysDepartment getSysDepartment() {
        return sysDepartment;
    }

    public void setSysDepartment(SysDepartment sysDepartment) {
        this.sysDepartment = sysDepartment;
    }

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public boolean hasRole(SysRole role) {
        Optional<SysRole> sysRoleOptional = sysRoles.stream().filter(p -> p.getId().equals(role.getId())).findFirst();
        return sysRoleOptional.isPresent();
    }

    public List<SysModule> getModules() {
        return modules;
    }

    public void setModules(List<SysModule> modules) {
        this.modules = modules;
    }
}
