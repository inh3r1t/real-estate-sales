package com.zx.base.model;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/28 19:01
 */
public class PageCondition {

    private int pageSize;
    private int page;
    private int start;
    private String orderField;
    private String orderType; // DESC,ASC

    public PageCondition() {
    }

    public PageCondition(int pageSize, int page, String orderField, String orderType) {
        this.pageSize = pageSize;
        this.page = page;
        this.start = pageSize * (page - 1);
        this.orderField = orderField;
        this.orderType = orderType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
