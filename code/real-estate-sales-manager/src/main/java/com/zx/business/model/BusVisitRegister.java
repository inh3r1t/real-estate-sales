package com.zx.business.model;

import com.zx.business.vo.PageVO;

import java.util.Date;

/**
 * 来访登记
 *
 * @author weiyi
 */
public class BusVisitRegister extends PageVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 楼盘主键
     */
    private Integer realEstateId;

    public String getRealEstateName() {
        return realEstateName;
    }

    public void setRealEstateName(String realEstateName) {
        this.realEstateName = realEstateName;
    }

    /**
     * 楼盘名称
     */
    private String realEstateName;

    /**
     * 客户
     */
    private String customer;

    /**
     * 联系方式
     */
    private String phone;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * 所属团队
     */
    private String team;

    /**
     * 置业顾问
     */
    private String adviser;

    /**
     * 居住区域r
     */
    private String address;

    /**
     * 职业r
     */
    private String occupation;

    /**
     * 到访次数
     */
    private String times;

    /**
     * 购买用途
     */
    private String purpose;

    /**
     * 付款方式
     */
    private String payment;

    /**
     * 意向产品
     */
    private String productType;

    /**
     * 意向面积
     */
    private String area;

    /**
     * 意向等级
     */
    private String level;

    /**
     * 购买意向
     */
    private String intention;

    /**
     * 未成交原因
     */
    private String nodeal;

    /**
     * 报备人属性
     */
    private String property;

    /**
     * 报备人姓名
     */
    private String reporter;

    /**
     * 创建用户
     */
    private Integer createrid;

    /**
     * 备注
     */
    private String remark;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    private Date createtime;


    public BusVisitRegister() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(Integer realEstateId) {
        this.realEstateId = realEstateId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdviser() {
        return adviser;
    }

    public void setAdviser(String adviser) {
        this.adviser = adviser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIntention() {
        return intention;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    public String getNodeal() {
        return nodeal;
    }

    public void setNodeal(String nodeal) {
        this.nodeal = nodeal;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreaterid() {
        return createrid;
    }

    public void setCreaterid(Integer createrid) {
        this.createrid = createrid;
    }
}
