package com.zx.base.model;

import java.io.Serializable;

import java.util.List;

/**
 *
 * @author V.E.
 * @param <T>
 */
public class PagerModel<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7253454781786513930L;

	/** 总数 */
    public int TotalItemCount;

    /** 每页记录数 */
    public int PageSize;

    /** 当前页数 */
    public int CurrentPage;

    /** 当前页数据 */
    public List<T> Items;

    /**
     * Constructs ...
     *
     *
     * @param pageSize 每页条数
     * @param currentPage 当前页
     * @param totalItemCount 总数
     * @param items 当前页数量
     */
    public PagerModel(int pageSize, int currentPage, int totalItemCount, List<T> items) {
        this.TotalItemCount = totalItemCount;
        this.CurrentPage    = currentPage;
        this.PageSize       = pageSize;
        this.Items          = items;
    }
}


