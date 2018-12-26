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
