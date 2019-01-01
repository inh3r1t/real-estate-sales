package com.zx.business.vo;

import java.io.Serializable;
import java.util.Date;

public class BusDealVO extends PageVO implements Serializable {

    private Integer dealId; // 订单id
    private String realEstateIds; // 楼盘ids,用","隔开
    private Integer reportUserId; // 报备人id - 中介
    private String customerName; // 客户名称
    private String customerPhone; // 客户电话
    private Integer customerSex; // 客户性别 0-男,1-女
    private Date reportTime; // 报备时间

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getDealId() {
        return dealId;
    }

    public void setDealId(Integer dealId) {
        this.dealId = dealId;
    }

    public Integer getCustomerSex() {
        return customerSex;
    }

    public void setCustomerSex(Integer customerSex) {
        this.customerSex = customerSex;
    }

    public String getRealEstateIds() {
        return realEstateIds;
    }

    public void setRealEstateIds(String realEstateIds) {
        this.realEstateIds = realEstateIds;
    }

    public Integer getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Integer reportUserId) {
        this.reportUserId = reportUserId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
