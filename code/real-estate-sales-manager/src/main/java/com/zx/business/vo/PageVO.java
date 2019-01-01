package com.zx.business.vo;

import java.io.Serializable;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/26 22:18
 */
public class PageVO implements Serializable {

    private Integer page;
    private Integer pageSize;
    private String orderField;
    private String orderType;

    private String formId;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
