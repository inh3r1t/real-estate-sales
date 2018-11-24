package com.zx.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wangx
 */
@SuppressWarnings("serial")
public class SysModule implements Serializable {

    private Integer id;
    /**
     * 模块编码
     */
    private String mcode;
    /**
     * 模块名称
     */
    @JsonProperty("name")
    @NotBlank(message = "模块名称不能为空")
    @Size(max = 20,min = 2,message = "模块名称长度为 {min}-{max} 个字符串")
    private String mname;
    /**
     * 模块分类
     */
    @JsonIgnore
    private String mtype;
    /**
     * Icon图标
     */
    @JsonIgnore
    @Size(max = 50,message = "图标长度最大{max}位")
    private String micon;
    /**
     *访问地址
     */
    @JsonIgnore
    @Size(max = 200,message = "访问地址长度最大{max}位")
    private String murl;
    /**
     * 状态 0=正常 -1=删除  1=禁用
     **/
    @JsonIgnore
    private Integer state;
    /**
     * 显示顺序
     **/
    @JsonIgnore
    private Integer sortnum;
    /**
     * 创建时间
     **/
    @JsonIgnore
    private Date createtime;
    /**
     * 备注
     **/
    @Size(max = 100, message = "备注长度最大{max}位")
    @JsonIgnore
    private String remark;
    /**
     * 别名
     **/
    @Size(max = 50, message = "别名长度最大{max}位")
    @JsonIgnore
    private String alias;
    /**
     * 父节点id
     **/
    @JsonProperty("pId")
    private Integer parentid;

    /**
     * 辅助参数
     **/
    @JsonIgnore
    private String parentcode;
    @JsonIgnore
    private String parentname;

    private List<SysModule> children;


    private boolean checked;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMcode() {
        return this.mcode;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMname() {
        return this.mname;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getMtype() {
        return this.mtype;
    }

    public void setMicon(String micon) {
        this.micon = micon;
    }

    public String getMicon() {
        return this.micon;
    }

    public void setMurl(String murl) {
        this.murl = murl;
    }

    public String getMurl() {
        return this.murl;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public Integer getSortnum() {
        return this.sortnum;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getParentcode() {
        if (parentcode != null && parentcode.trim().length() >= 3) {
            return parentcode;
        }
        if (mcode != null && mcode.trim() != "" && mcode.length() > 3) {
            return mcode.substring(0, mcode.length() - 3);
        }
        return "";
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<SysModule> getChildren() {
        return children;
    }

    public void setChildren(List<SysModule> children) {
        this.children = children;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
