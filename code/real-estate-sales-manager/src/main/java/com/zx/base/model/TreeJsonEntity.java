package com.zx.base.model;

import java.util.List;


/**
 *
 * @author feidengke
 * @version 2017/12/06
 */
public class TreeJsonEntity {
    /**
     * 节点Id
     */
    public Integer id;
    /**
     * 节点编码
     */
    public String code;

    /**
     * 显示文本
     */
    public String text;

    /**
     * 文本对应值
     */
    public String value;

    /**
     * 复选状态
     */
    public Integer checkState;


    /**
     * 是否显示复选框
     */
    public boolean showCheck;

    /**
     * 是否展开
     */
    public boolean isExpand;


    /**
     * 是否完成
     */
    public boolean complete;

    /**
     * 是否有子节点
     */
    public boolean hasChildren;

    /**
     * 子节点对象
     */
    public List<TreeJsonEntity> childNodes;

    /**
     * icon图标
     */
    public String img;

    /**
     * 显示标题
     */
    public String title;


    /**
     * 扩展属性对象
     */
    public Object extra;
}
