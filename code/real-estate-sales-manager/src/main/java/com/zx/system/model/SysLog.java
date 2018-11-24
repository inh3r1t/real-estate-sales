package com.zx.system.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author wangx
 */
@SuppressWarnings("serial")
public class SysLog implements Serializable {

    /**
     * 唯一标识
     **/
    private BigInteger id;

    /**
     * 日志记录Url
     **/
    private String url;

    /**
     * 系统模块编码
     **/
    private String mcode;

    /**
     * 系统模块名称
     **/
    private String mname;

    /**
     * 日志简述
     **/
    private String description;

    /**
     * 日志详情
     **/
    private String details;

    /**
     * 操作类型(枚举值)
     **/
    private Integer logtype;

    /**
     * 操作类型 (枚举名称)
     **/
    private String logtypename;

    /**
     * 日志等级 (枚值称)
     **/
    private Integer loglevel;

    /**
     * 日志等级 (枚举名称)
     **/
    private String loglevelname;

    /**
     * 日志参数
     **/
    private String logargs;

    /**
     * 客户端Ip
     **/
    private String clientip;

    /**
     * 客户端浏览器
     **/
    private String clientbrowser;

    /**
     * 客户端信息
     **/
    private String clientos;


    /**
     * 记录人
     **/
    private String creater;
    /**
     * 记录人ID
     **/
    private String createrid;
    /**
     * 创建时间
     **/
    private Date createtime;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getLogtype() {
        return logtype;
    }

    public void setLogtype(Integer logtype) {
        this.logtype = logtype;
    }

    public String getLogtypename() {
        return logtypename;
    }

    public void setLogtypename(String logtypename) {
        this.logtypename = logtypename;
    }

    public Integer getLoglevel() {
        return loglevel;
    }

    public void setLoglevel(Integer loglevel) {
        this.loglevel = loglevel;
    }

    public String getLoglevelname() {
        return loglevelname;
    }

    public void setLoglevelname(String loglevelname) {
        this.loglevelname = loglevelname;
    }

    public String getLogargs() {
        return logargs;
    }

    public void setLogargs(String logargs) {
        this.logargs = logargs;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public String getClientbrowser() {
        return clientbrowser;
    }

    public void setClientbrowser(String clientbrowser) {
        this.clientbrowser = clientbrowser;
    }

    public String getClientos() {
        return clientos;
    }

    public void setClientos(String clientos) {
        this.clientos = clientos;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 日志类型
     **/
    public enum LogType {

        新增(0),

        修改(1),

        删除(2),

        关联(3),

        接口(4),

        配置(5),

        其他(9);

        int index;

        LogType(int index) {
            this.index = index;
        }

        public int getValue() {
            return index;
        }

        public static String getName(int index) {
            for (LogType type : LogType.values()) {
                if (type.getValue() == index) {
                    return type.name();
                }
            }
            return "";
        }

    }

    public enum LogLevel {
        信息(1), 警告(2), 错误(3);

        int index;

        LogLevel(int index) {
            this.index = index;
        }

        public int getValue() {
            return index;
        }

        public static String getName(int index) {
            for (LogLevel type : LogLevel.values()) {
                if (type.getValue() == index) {
                    return type.name();
                }
            }
            return "";
        }
    }

}
