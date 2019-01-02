package com.zx.business.model;

import com.zx.business.vo.PageVO;

import java.util.Date;

public class BusDeal extends PageVO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.real_estate_id
     *
     * @mbg.generated
     */
    private Integer realEstateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.real_estate_name
     *
     * @mbg.generated
     */
    private String realEstateName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.state
     *
     * @mbg.generated
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.customer_id
     *
     * @mbg.generated
     */
    private Integer customerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.customer_phone
     *
     * @mbg.generated
     */
    private String customerPhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.customer_name
     *
     * @mbg.generated
     */
    private String customerName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.report_user_id
     *
     * @mbg.generated
     */
    private Integer reportUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.report_user_phone
     *
     * @mbg.generated
     */
    private String reportUserPhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.report_company
     *
     * @mbg.generated
     */
    private String reportCompany;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.appointment_time
     *
     * @mbg.generated
     */
    private Date appointmentTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.report_time
     *
     * @mbg.generated
     */
    private Date reportTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.arrive_time
     *
     * @mbg.generated
     */
    private Date arriveTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.subscribe_time
     *
     * @mbg.generated
     */
    private Date subscribeTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.manager_id
     *
     * @mbg.generated
     */
    private Integer managerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.manager_phone
     *
     * @mbg.generated
     */
    private String managerPhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.arrive_certify_photo_path
     *
     * @mbg.generated
     */
    private String arriveCertifyPhotoPath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.subscribe_money
     *
     * @mbg.generated
     */
    private String subscribeMoney;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.subscribe_commision
     *
     * @mbg.generated
     */
    private String subscribeCommision;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.subscribe_photo_pahts
     *
     * @mbg.generated
     */
    private String subscribePhotoPahts;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.appointment_operate_time
     *
     * @mbg.generated
     */
    private Date appointmentOperateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.report_operate_time
     *
     * @mbg.generated
     */
    private Date reportOperateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.arrive_operate_time
     *
     * @mbg.generated
     */
    private Date arriveOperateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.subscribe_operate_time
     *
     * @mbg.generated
     */
    private Date subscribeOperateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_deal.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    private BusRealEstate busRealEstate;
    private BusCustomer busCustomer;
    private BusUser reportUser; // 报备人
    private BusUser manager; // 项目经理

    private Date signTime;
    private Date signOperateTime;
    private String signMoney;

    public BusDeal(Integer id, Integer realEstateId, String realEstateName, Integer state, Integer customerId, String customerPhone, String customerName, Integer reportUserId, String reportUserPhone, String reportCompany, Date appointmentTime, Date reportTime, Date arriveTime, Date subscribeTime, Integer managerId, String managerPhone, String arriveCertifyPhotoPath, String subscribeMoney, String subscribeCommision, String subscribePhotoPahts, Date appointmentOperateTime, Date reportOperateTime, Date arriveOperateTime, Date subscribeOperateTime, Date createTime, Date updateTime, BusRealEstate busRealEstate, BusCustomer busCustomer, BusUser reportUser, BusUser manager, Date signTime, Date signOperateTime, String signMoney) {
        this.id = id;
        this.realEstateId = realEstateId;
        this.realEstateName = realEstateName;
        this.state = state;
        this.customerId = customerId;
        this.customerPhone = customerPhone;
        this.customerName = customerName;
        this.reportUserId = reportUserId;
        this.reportUserPhone = reportUserPhone;
        this.reportCompany = reportCompany;
        this.appointmentTime = appointmentTime;
        this.reportTime = reportTime;
        this.arriveTime = arriveTime;
        this.subscribeTime = subscribeTime;
        this.managerId = managerId;
        this.managerPhone = managerPhone;
        this.arriveCertifyPhotoPath = arriveCertifyPhotoPath;
        this.subscribeMoney = subscribeMoney;
        this.subscribeCommision = subscribeCommision;
        this.subscribePhotoPahts = subscribePhotoPahts;
        this.appointmentOperateTime = appointmentOperateTime;
        this.reportOperateTime = reportOperateTime;
        this.arriveOperateTime = arriveOperateTime;
        this.subscribeOperateTime = subscribeOperateTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.busRealEstate = busRealEstate;
        this.busCustomer = busCustomer;
        this.reportUser = reportUser;
        this.manager = manager;
        this.signTime = signTime;
        this.signOperateTime = signOperateTime;
        this.signMoney = signMoney;
    }

    public String getSignMoney() {
        return signMoney;
    }

    public void setSignMoney(String signMoney) {
        this.signMoney = signMoney;
    }

    public BusRealEstate getBusRealEstate() {
        return busRealEstate;
    }

    public void setBusRealEstate(BusRealEstate busRealEstate) {
        this.busRealEstate = busRealEstate;
    }

    public BusCustomer getBusCustomer() {
        return busCustomer;
    }

    public void setBusCustomer(BusCustomer busCustomer) {
        this.busCustomer = busCustomer;
    }

    public BusUser getReportUser() {
        return reportUser;
    }

    public void setReportUser(BusUser reportUser) {
        this.reportUser = reportUser;
    }

    public BusUser getManager() {
        return manager;
    }

    public void setManager(BusUser manager) {
        this.manager = manager;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_deal
     *
     * @mbg.generated
     */
    public BusDeal() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.id
     *
     * @return the value of bus_deal.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.id
     *
     * @param id the value for bus_deal.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.real_estate_id
     *
     * @return the value of bus_deal.real_estate_id
     *
     * @mbg.generated
     */
    public Integer getRealEstateId() {
        return realEstateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.real_estate_id
     *
     * @param realEstateId the value for bus_deal.real_estate_id
     *
     * @mbg.generated
     */
    public void setRealEstateId(Integer realEstateId) {
        this.realEstateId = realEstateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.real_estate_name
     *
     * @return the value of bus_deal.real_estate_name
     *
     * @mbg.generated
     */
    public String getRealEstateName() {
        return realEstateName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.real_estate_name
     *
     * @param realEstateName the value for bus_deal.real_estate_name
     *
     * @mbg.generated
     */
    public void setRealEstateName(String realEstateName) {
        this.realEstateName = realEstateName == null ? null : realEstateName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.state
     *
     * @return the value of bus_deal.state
     *
     * @mbg.generated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.state
     *
     * @param state the value for bus_deal.state
     *
     * @mbg.generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.customer_id
     *
     * @return the value of bus_deal.customer_id
     *
     * @mbg.generated
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.customer_id
     *
     * @param customerId the value for bus_deal.customer_id
     *
     * @mbg.generated
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.customer_phone
     *
     * @return the value of bus_deal.customer_phone
     *
     * @mbg.generated
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.customer_phone
     *
     * @param customerPhone the value for bus_deal.customer_phone
     *
     * @mbg.generated
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone == null ? null : customerPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.customer_name
     *
     * @return the value of bus_deal.customer_name
     *
     * @mbg.generated
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.customer_name
     *
     * @param customerName the value for bus_deal.customer_name
     *
     * @mbg.generated
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.report_user_id
     *
     * @return the value of bus_deal.report_user_id
     *
     * @mbg.generated
     */
    public Integer getReportUserId() {
        return reportUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.report_user_id
     *
     * @param reportUserId the value for bus_deal.report_user_id
     *
     * @mbg.generated
     */
    public void setReportUserId(Integer reportUserId) {
        this.reportUserId = reportUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.report_user_phone
     *
     * @return the value of bus_deal.report_user_phone
     *
     * @mbg.generated
     */
    public String getReportUserPhone() {
        return reportUserPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.report_user_phone
     *
     * @param reportUserPhone the value for bus_deal.report_user_phone
     *
     * @mbg.generated
     */
    public void setReportUserPhone(String reportUserPhone) {
        this.reportUserPhone = reportUserPhone == null ? null : reportUserPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.report_company
     *
     * @return the value of bus_deal.report_company
     *
     * @mbg.generated
     */
    public String getReportCompany() {
        return reportCompany;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.report_company
     *
     * @param reportCompany the value for bus_deal.report_company
     *
     * @mbg.generated
     */
    public void setReportCompany(String reportCompany) {
        this.reportCompany = reportCompany == null ? null : reportCompany.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.appointment_time
     *
     * @return the value of bus_deal.appointment_time
     *
     * @mbg.generated
     */
    public Date getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.appointment_time
     *
     * @param appointmentTime the value for bus_deal.appointment_time
     *
     * @mbg.generated
     */
    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.report_time
     *
     * @return the value of bus_deal.report_time
     *
     * @mbg.generated
     */
    public Date getReportTime() {
        return reportTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.report_time
     *
     * @param reportTime the value for bus_deal.report_time
     *
     * @mbg.generated
     */
    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.arrive_time
     *
     * @return the value of bus_deal.arrive_time
     *
     * @mbg.generated
     */
    public Date getArriveTime() {
        return arriveTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.arrive_time
     *
     * @param arriveTime the value for bus_deal.arrive_time
     *
     * @mbg.generated
     */
    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.subscribe_time
     *
     * @return the value of bus_deal.subscribe_time
     *
     * @mbg.generated
     */
    public Date getSubscribeTime() {
        return subscribeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.subscribe_time
     *
     * @param subscribeTime the value for bus_deal.subscribe_time
     *
     * @mbg.generated
     */
    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.manager_id
     *
     * @return the value of bus_deal.manager_id
     *
     * @mbg.generated
     */
    public Integer getManagerId() {
        return managerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.manager_id
     *
     * @param managerId the value for bus_deal.manager_id
     *
     * @mbg.generated
     */
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.manager_phone
     *
     * @return the value of bus_deal.manager_phone
     *
     * @mbg.generated
     */
    public String getManagerPhone() {
        return managerPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.manager_phone
     *
     * @param managerPhone the value for bus_deal.manager_phone
     *
     * @mbg.generated
     */
    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone == null ? null : managerPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.arrive_certify_photo_path
     *
     * @return the value of bus_deal.arrive_certify_photo_path
     *
     * @mbg.generated
     */
    public String getArriveCertifyPhotoPath() {
        return arriveCertifyPhotoPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.arrive_certify_photo_path
     *
     * @param arriveCertifyPhotoPath the value for bus_deal.arrive_certify_photo_path
     *
     * @mbg.generated
     */
    public void setArriveCertifyPhotoPath(String arriveCertifyPhotoPath) {
        this.arriveCertifyPhotoPath = arriveCertifyPhotoPath == null ? null : arriveCertifyPhotoPath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.subscribe_money
     *
     * @return the value of bus_deal.subscribe_money
     *
     * @mbg.generated
     */
    public String getSubscribeMoney() {
        return subscribeMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.subscribe_money
     *
     * @param subscribeMoney the value for bus_deal.subscribe_money
     *
     * @mbg.generated
     */
    public void setSubscribeMoney(String subscribeMoney) {
        this.subscribeMoney = subscribeMoney == null ? null : subscribeMoney.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.subscribe_commision
     *
     * @return the value of bus_deal.subscribe_commision
     *
     * @mbg.generated
     */
    public String getSubscribeCommision() {
        return subscribeCommision;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.subscribe_commision
     *
     * @param subscribeCommision the value for bus_deal.subscribe_commision
     *
     * @mbg.generated
     */
    public void setSubscribeCommision(String subscribeCommision) {
        this.subscribeCommision = subscribeCommision == null ? null : subscribeCommision.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.subscribe_photo_pahts
     *
     * @return the value of bus_deal.subscribe_photo_pahts
     *
     * @mbg.generated
     */
    public String getSubscribePhotoPahts() {
        return subscribePhotoPahts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.subscribe_photo_pahts
     *
     * @param subscribePhotoPahts the value for bus_deal.subscribe_photo_pahts
     *
     * @mbg.generated
     */
    public void setSubscribePhotoPahts(String subscribePhotoPahts) {
        this.subscribePhotoPahts = subscribePhotoPahts == null ? null : subscribePhotoPahts.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.appointment_operate_time
     *
     * @return the value of bus_deal.appointment_operate_time
     *
     * @mbg.generated
     */
    public Date getAppointmentOperateTime() {
        return appointmentOperateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.appointment_operate_time
     *
     * @param appointmentOperateTime the value for bus_deal.appointment_operate_time
     *
     * @mbg.generated
     */
    public void setAppointmentOperateTime(Date appointmentOperateTime) {
        this.appointmentOperateTime = appointmentOperateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.report_operate_time
     *
     * @return the value of bus_deal.report_operate_time
     *
     * @mbg.generated
     */
    public Date getReportOperateTime() {
        return reportOperateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.report_operate_time
     *
     * @param reportOperateTime the value for bus_deal.report_operate_time
     *
     * @mbg.generated
     */
    public void setReportOperateTime(Date reportOperateTime) {
        this.reportOperateTime = reportOperateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.arrive_operate_time
     *
     * @return the value of bus_deal.arrive_operate_time
     *
     * @mbg.generated
     */
    public Date getArriveOperateTime() {
        return arriveOperateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.arrive_operate_time
     *
     * @param arriveOperateTime the value for bus_deal.arrive_operate_time
     *
     * @mbg.generated
     */
    public void setArriveOperateTime(Date arriveOperateTime) {
        this.arriveOperateTime = arriveOperateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.subscribe_operate_time
     *
     * @return the value of bus_deal.subscribe_operate_time
     *
     * @mbg.generated
     */
    public Date getSubscribeOperateTime() {
        return subscribeOperateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.subscribe_operate_time
     *
     * @param subscribeOperateTime the value for bus_deal.subscribe_operate_time
     *
     * @mbg.generated
     */
    public void setSubscribeOperateTime(Date subscribeOperateTime) {
        this.subscribeOperateTime = subscribeOperateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.create_time
     *
     * @return the value of bus_deal.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.create_time
     *
     * @param createTime the value for bus_deal.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_deal.update_time
     *
     * @return the value of bus_deal.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_deal.update_time
     *
     * @param updateTime the value for bus_deal.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getSignOperateTime() {
        return signOperateTime;
    }

    public void setSignOperateTime(Date signOperateTime) {
        this.signOperateTime = signOperateTime;
    }
}