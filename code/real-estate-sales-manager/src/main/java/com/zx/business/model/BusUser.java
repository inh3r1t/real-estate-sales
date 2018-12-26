package com.zx.business.model;

import com.zx.business.vo.PageVO;

import java.util.Date;

public class BusUser extends PageVO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.open_id
     *
     * @mbg.generated
     */
    private String openId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.user_name
     *
     * @mbg.generated
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.passwd
     *
     * @mbg.generated
     */
    private String passwd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.phone_num
     *
     * @mbg.generated
     */
    private String phoneNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.poll_code
     *
     * @mbg.generated
     */
    private String pollCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.role_id
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.company_id
     *
     * @mbg.generated
     */
    private Integer companyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.company_name
     *
     * @mbg.generated
     */
    private String companyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_user.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_user
     *
     * @mbg.generated
     */
    public BusUser(Integer id, String openId, String userName, String passwd, String phoneNum, String pollCode, Integer roleId, Integer companyId, String companyName, Date createTime, Date updateTime) {
        this.id = id;
        this.openId = openId;
        this.userName = userName;
        this.passwd = passwd;
        this.phoneNum = phoneNum;
        this.pollCode = pollCode;
        this.roleId = roleId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_user
     *
     * @mbg.generated
     */
    public BusUser() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.id
     *
     * @return the value of bus_user.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.id
     *
     * @param id the value for bus_user.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.open_id
     *
     * @return the value of bus_user.open_id
     *
     * @mbg.generated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.open_id
     *
     * @param openId the value for bus_user.open_id
     *
     * @mbg.generated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.user_name
     *
     * @return the value of bus_user.user_name
     *
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.user_name
     *
     * @param userName the value for bus_user.user_name
     *
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.passwd
     *
     * @return the value of bus_user.passwd
     *
     * @mbg.generated
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.passwd
     *
     * @param passwd the value for bus_user.passwd
     *
     * @mbg.generated
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.phone_num
     *
     * @return the value of bus_user.phone_num
     *
     * @mbg.generated
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.phone_num
     *
     * @param phoneNum the value for bus_user.phone_num
     *
     * @mbg.generated
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.poll_code
     *
     * @return the value of bus_user.poll_code
     *
     * @mbg.generated
     */
    public String getPollCode() {
        return pollCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.poll_code
     *
     * @param pollCode the value for bus_user.poll_code
     *
     * @mbg.generated
     */
    public void setPollCode(String pollCode) {
        this.pollCode = pollCode == null ? null : pollCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.role_id
     *
     * @return the value of bus_user.role_id
     *
     * @mbg.generated
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.role_id
     *
     * @param roleId the value for bus_user.role_id
     *
     * @mbg.generated
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.company_id
     *
     * @return the value of bus_user.company_id
     *
     * @mbg.generated
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.company_id
     *
     * @param companyId the value for bus_user.company_id
     *
     * @mbg.generated
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.company_name
     *
     * @return the value of bus_user.company_name
     *
     * @mbg.generated
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.company_name
     *
     * @param companyName the value for bus_user.company_name
     *
     * @mbg.generated
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.create_time
     *
     * @return the value of bus_user.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.create_time
     *
     * @param createTime the value for bus_user.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_user.update_time
     *
     * @return the value of bus_user.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_user.update_time
     *
     * @param updateTime the value for bus_user.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}