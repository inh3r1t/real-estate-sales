package com.zx.business.vo;

import java.io.Serializable;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/26 22:18
 */
public class PageVO implements Serializable {

    private int page;
    private int pageSize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
