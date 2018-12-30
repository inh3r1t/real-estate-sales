package com.zx.business.model;

import com.zx.business.vo.PageVO;

import java.util.Date;
import java.util.List;

public class BusRealEstate extends PageVO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.summery
     *
     * @mbg.generated
     */
    private String summery;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.address
     *
     * @mbg.generated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.commission
     *
     * @mbg.generated
     */
    private String commission;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.manager_id
     *
     * @mbg.generated
     */
    private Integer managerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.manager_name
     *
     * @mbg.generated
     */
    private String managerName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.detail
     *
     * @mbg.generated
     */
    private String detail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.is_top_recommend
     *
     * @mbg.generated
     */
    private Boolean isTopRecommend;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.is_list_recommend
     *
     * @mbg.generated
     */
    private Boolean isListRecommend;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_real_estate.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    private String thumbnail;
    private List<String> images;

    public BusRealEstate(Integer id, String name, String summery, String address, String commission, Integer managerId, String managerName, String detail, Boolean isTopRecommend, Boolean isListRecommend, Date createTime, Date updateTime, String thumbnail, List<String> images) {
        this.id = id;
        this.name = name;
        this.summery = summery;
        this.address = address;
        this.commission = commission;
        this.managerId = managerId;
        this.managerName = managerName;
        this.detail = detail;
        this.isTopRecommend = isTopRecommend;
        this.isListRecommend = isListRecommend;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.thumbnail = thumbnail;
        this.images = images;
    }

    public Boolean getTopRecommend() {
        return isTopRecommend;
    }

    public void setTopRecommend(Boolean topRecommend) {
        isTopRecommend = topRecommend;
    }

    public Boolean getListRecommend() {
        return isListRecommend;
    }

    public void setListRecommend(Boolean listRecommend) {
        isListRecommend = listRecommend;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_real_estate
     *
     * @mbg.generated
     */
    public BusRealEstate(Integer id, String name, String summery, String address, String commission, Integer managerId, String managerName, String detail, Boolean isTopRecommend, Boolean isListRecommend, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.summery = summery;
        this.address = address;
        this.commission = commission;
        this.managerId = managerId;
        this.managerName = managerName;
        this.detail = detail;
        this.isTopRecommend = isTopRecommend;
        this.isListRecommend = isListRecommend;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_real_estate
     *
     * @mbg.generated
     */
    public BusRealEstate() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.id
     *
     * @return the value of bus_real_estate.id
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.id
     *
     * @param id the value for bus_real_estate.id
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.name
     *
     * @return the value of bus_real_estate.name
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.name
     *
     * @param name the value for bus_real_estate.name
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.summery
     *
     * @return the value of bus_real_estate.summery
     * @mbg.generated
     */
    public String getSummery() {
        return summery;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.summery
     *
     * @param summery the value for bus_real_estate.summery
     * @mbg.generated
     */
    public void setSummery(String summery) {
        this.summery = summery == null ? null : summery.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.address
     *
     * @return the value of bus_real_estate.address
     * @mbg.generated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.address
     *
     * @param address the value for bus_real_estate.address
     * @mbg.generated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.commission
     *
     * @return the value of bus_real_estate.commission
     * @mbg.generated
     */
    public String getCommission() {
        return commission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.commission
     *
     * @param commission the value for bus_real_estate.commission
     * @mbg.generated
     */
    public void setCommission(String commission) {
        this.commission = commission == null ? null : commission.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.manager_id
     *
     * @return the value of bus_real_estate.manager_id
     * @mbg.generated
     */
    public Integer getManagerId() {
        return managerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.manager_id
     *
     * @param managerId the value for bus_real_estate.manager_id
     * @mbg.generated
     */
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.manager_name
     *
     * @return the value of bus_real_estate.manager_name
     * @mbg.generated
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.manager_name
     *
     * @param managerName the value for bus_real_estate.manager_name
     * @mbg.generated
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.detail
     *
     * @return the value of bus_real_estate.detail
     * @mbg.generated
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.detail
     *
     * @param detail the value for bus_real_estate.detail
     * @mbg.generated
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.is_top_recommend
     *
     * @return the value of bus_real_estate.is_top_recommend
     * @mbg.generated
     */
    public Boolean getIsTopRecommend() {
        return isTopRecommend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.is_top_recommend
     *
     * @param isTopRecommend the value for bus_real_estate.is_top_recommend
     * @mbg.generated
     */
    public void setIsTopRecommend(Boolean isTopRecommend) {
        this.isTopRecommend = isTopRecommend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.is_list_recommend
     *
     * @return the value of bus_real_estate.is_list_recommend
     * @mbg.generated
     */
    public Boolean getIsListRecommend() {
        return isListRecommend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.is_list_recommend
     *
     * @param isListRecommend the value for bus_real_estate.is_list_recommend
     * @mbg.generated
     */
    public void setIsListRecommend(Boolean isListRecommend) {
        this.isListRecommend = isListRecommend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.create_time
     *
     * @return the value of bus_real_estate.create_time
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.create_time
     *
     * @param createTime the value for bus_real_estate.create_time
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_real_estate.update_time
     *
     * @return the value of bus_real_estate.update_time
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_real_estate.update_time
     *
     * @param updateTime the value for bus_real_estate.update_time
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}